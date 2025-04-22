const usuarioId = document.currentScript.getAttribute("usuario-id");
const chatId = document.currentScript.getAttribute("chat-id");
//const contextPath = document.currentScript.getAttribute("contextPath");

actualizarScroll();
let tokenCsrf;
let stompClient;

getTokenCSRF().then(token=>{
    tokenCsrf = token.token;
    console.log(tokenCsrf);
    console.log("Token obtenido")

    stompClient = new StompJs.Client({
        brokerURL: 'ws://localhost:8080/fbtripulantes/wsfbtripulanteschat',
        connectHeaders:{
            "X-CSRF-TOKEN":tokenCsrf
        }
    });

    stompClient.debug = function(str) {
        console.log(str)
    };

    stompClient.onConnect = ({"X-CSRF-TOKEN":tokenCsrf},frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/recibir', (notificacion) => {
            console.log(notificacion);
            recibirMensaje();
        });
    };

    stompClient.onWebSocketError = (error) => {
        console.error('Error with websocket', error);
    };

    stompClient.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };

    stompClient.activate();
})
.catch(()=>{
    console.log("Error con el token")
})


function enviarMensaje(){
    let mensaje = document.getElementById("mensaje").value;
    let inputMensaje = document.getElementById("mensaje");
    inputMensaje.value = "";

    let chatId = document.getElementById(("chat")).value;
    stompClient.publish({
        destination: "/app/enviar",
        body: JSON.stringify({
            idChat: chatId,
            mensaje: mensaje,
            idUsuario: usuarioId,
            csrfToken:tokenCsrf
        })
    });
}

function recibirMensaje(){
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/fbtripulantes/getMensajesByChat?id="+chatId,
        dataType: "json",
        headers: {
            "X-CSRF-TOKEN":tokenCsrf
        },
        success: function(mensajesJson){
            //console.log(mensajesJson);
            cargarMensajes(mensajesJson);
            actualizarScroll();
        }
    })
}

//FALTA: AGREGAR LOS MESNAJES ACTUALIZADOS A CHAT CONTENT CLASS.
function cargarMensajes(messages){
    let chatContent = document.querySelector(".chatContent");
    vaciar(chatContent);

    messages.forEach(message => {
        let fechaEnvioFormato = obtenerFormatoFecha(message.fechaEnvio);
        
        if(message.usuario.id === message.chat.destinatario.id){
            let reverseDestinatario = (usuarioId == message.chat.destinatario.id) ? "flex-direction:row-reverse" : "";

            chatContent.innerHTML += `
         <div style="width: 100%">
         <div class="col">
         <div>
         <div>
         <div>
         <div class="mensajes_container">
                <div class="chat-message-right p-4 destinatario__messages" style="display: flex; ${reverseDestinatario}">
                    <div>
                    <img src="https://i.pravatar.cc/150?u=a042581f4e29026704d" class="rounded-circle mr-1" alt="Chris Wood" width="40" height="40">
                        <div class="text-muted small text-nowrap mt-2">
                            <span class="small text-start fw-lighter">${fechaEnvioFormato}</span>
                        </div>
                    </div>
                    <div class="flex-shrink-1 bg-light rounded py-2 px-3 mr-3">
                        <div class="font-weight-bold mb-1">
                            <span class="text-start fw-bold" >${message.usuario.username}</span>
                        </div>
                        <span class="medium text-start fw-medium">${message.mensaje}</span>
                    </div>
                </div>
                <p></p>
                </div>
                
                </div>
                </div>
                </div>
                </div>
                </div>
            `
        }else{
            let reverseRemitente = (usuarioId == message.chat.remitente.id) ? "flex-direction:row-reverse" : "";

            chatContent.innerHTML+=`
         <div style="width: 100%">
         <div class="col">
         <div>
         <div>
         <div>
         
             <div class="mensajes_container remitente__messages">
                <div class="chat-message-right p-4" style="display: flex; ${reverseRemitente}">
                    <div>
                    <img src="https://i.pravatar.cc/150?u=a042581f4e29026704d" class="rounded-circle mr-1" alt="Chris Wood" width="40" height="40">
                        <div class="text-muted small text-nowrap mt-2">
                            <span class="small text-start fw-lighter">${fechaEnvioFormato}</span>
                        </div>
                    </div>
                    <div class="flex-shrink-1 bg-light rounded py-2 px-3 mr-3">
                        <div class="font-weight-bold mb-1">
                            <span class="text-start fw-bold" >${message.usuario.username}</span>
                        </div>
                        <span class="medium text-start fw-medium">${message.mensaje}</span>
                    </div>
                </div>
                <p></p>
                </div>
                
                              </div>
                </div>
                </div>
                </div>
                </div>
            `;
        }
    })

}

//'dd/MM hh:mm'
function obtenerFormatoFecha(fecha){
    let fechaDate = new Date(fecha);

    if(fechaDate.getHours()>12)
        fechaDate.setHours(getMapHours()[fechaDate.getHours()]);

    let formatoDeFecha = (fechaDate.getDate()>9) ? fechaDate.getDate() : `0${fechaDate.getDate()}`;
    formatoDeFecha +="/";
    formatoDeFecha +=   (fechaDate.getMonth()+1>9) ? fechaDate.getMonth()+1 : `0${fechaDate.getMonth()+1}`;
    formatoDeFecha += " ";
    formatoDeFecha +=  (fechaDate.getHours()>9) ? fechaDate.getHours() : `0${fechaDate.getHours()}`;
    formatoDeFecha += ":";
    formatoDeFecha +=  (fechaDate.getMinutes()>9) ? fechaDate.getMinutes() : `0${fechaDate.getMinutes()}`;

    return formatoDeFecha
}

function getMapHours(){
    const mapHoras = [];
    for(let i = 1 ; i<=12; i++)
        mapHoras[12+i] = i;

    return mapHoras;
}
function actualizarScroll(){
    const elemento = document.getElementById("containerChat");
    elemento.scrollTop = elemento.scrollHeight-elemento.clientHeight;
}
function vaciar(elementHtml){
    elementHtml.innerHTML = "";
}

//Llamada al token CSRF
async function getTokenCSRF(){
    let tokenCsrf=await fetch("http://localhost:8080/fbtripulantes/csrf");
    tokenCsrf = await tokenCsrf.json();
    return tokenCsrf;
}


document.getElementById("enviarMensajeBoton").addEventListener("click", (e) => {
    enviarMensaje();
});