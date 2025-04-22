
const aeropuertos = JSON.parse(document.currentScript.getAttribute("aeropuertos"));
const actividades = JSON.parse(document.currentScript.getAttribute("actividades"));
const vuelos = JSON.parse(document.currentScript.getAttribute("vuelos"));

const element = document.getElementById('handsontable-grid');
/*
const defaultData = Array.from({length:15}, () =>({
    date: '04-Feb-2025',  // Fecha por defecto
    typeActivity: actividades[0],  // Actividad por defecto
    airportOrigin: `${aeropuertos[0].nombre}(${aeropuertos[0].codigo})`,  // Aeropuerto de origen por defecto
    airportTarget: `${aeropuertos[0].nombre}(${aeropuertos[0].codigo})`,  // Aeropuerto de destino por defecto
    presentationDate: '04-Feb-2025',  // Fecha de presentación por defecto
    presentationTime: '10:00:00',  // Hora de presentación por defecto
    landingDate: '04-Feb-2025',  // Fecha de aterrizaje por defecto
    landingTime: '12:00:00',  // Hora de aterrizaje por defecto
    takeOffDate: '04-Feb-2025',  // Fecha de despegue por defecto
    takeOffTime: '14:00:00',  // Hora de despegue por defecto
//    checkToCalendar: false  // Checkbox por defecto desmarcado
}));*/
const defaultData = Array.from({length:15}, () =>({
    date: '',  // Fecha por defecto
    typeActivity: '',  // Actividad por defecto
    airportOrigin: '',  // Aeropuerto de origen por defecto
    airportTarget:'',  // Aeropuerto de destino por defecto
    presentationDate: '',  // Fecha de presentación por defecto
    presentationTime: '00:00:00',  // Hora de presentación por defecto
    landingDate:'',  // Fecha de aterrizaje por defecto
    landingTime: '00:00:00',  // Hora de aterrizaje por defecto
    takeOffDate: '',  // Fecha de despegue por defecto
    takeOffTime: '00:00:00',  // Hora de despegue por defecto
//    checkToCalendar: false  // Checkbox por defecto desmarcado
}));


const configuration = {
    data: defaultData,
    columns: [
        {data: 'date', title: 'Dia de actividad', width: 75, type:'date', dateFormat: 'DD-MMM-YYYY' },
        {data: 'typeActivity', title: 'Tipo de actividad', width: 110, type: 'dropdown', source: actividades},
       // {data: 'flight', title: 'Vuelo', width: 130, type: 'dropdown',
         //   source: vuelos.map(v=> `${v.aeropuertoOrigen.codigo} - ${v.aeropuertoDestino.codigo}`)},

        {data: 'airportOrigin', title: 'Aeropuerto Origen', width: 105, type: 'dropdown', source:
            aeropuertos.map(a=> `${a.nombre}(${a.codigo})`),
        },

        {data: 'airportTarget', title: 'Aeropuerto Destino', width: 105,type: 'dropdown', source:
                aeropuertos.map(a=> `${a.nombre}(${a.codigo})`),
         },

        {data: 'presentationDate', title: 'Fecha de presentacion', width: 120, type: 'date', dateFormat:'DD-MMM-YYYY'},
        {data: 'presentationTime', title: 'Hora de presentacion', width: 120, type: 'time', timeFormat:'HH:mm:ss',correctFormat:true},
        {data: 'landingDate', title: 'Fecha de aterrizaje', width: 120, type: 'date', dateFormat:'DD-MMM-YYYY'},
        {data: 'landingTime', title: 'Hora de aterrizaje', width: 120, type: 'time', timeFormat:'HH:mm:ss',correctFormat:true},
        {data: 'takeOffDate', title: 'Fecha de despegue', width: 120, type: 'date', dateFormat:'DD-MMM-YYYY'},
        {data: 'takeOffTime', title: 'Hora de despegue', width: 120, type: 'time', timeFormat:'HH:mm:ss',correctFormat:true}
        //{data: 'checkToCalendar', title: 'Agregar al calendario', width: 120, type: 'checkbox'}
        ],
        className: 'table-style',
        colHeaders: true,
        rowHeaders: true,
        multiSelect:true,
        navigableHeaders: true,
        tabNavigation: true,
        multiColumnSorting: true,
        //selectionMode: 'single', // 'single', 'range' or 'multiple',
        headerClassName: 'htLeft',
        select: true,  // Habilita la selección de celdas o filas
        licenseKey: 'non-commercial-and-evaluation',

    };

const table = new Handsontable(element, configuration);



console.log(aeropuertos);
console.log(actividades);
//console.log(vuelos);



let tokenCsrf;
const username = document.currentScript.getAttribute("username");
const password = document.currentScript.getAttribute("password");
console.log(username+ " + "+password)

document.getElementById("guardar-button").addEventListener("click", ()=>{
    const dataModificados = table.getData();
    console.log(dataModificados);
    console.log(JSON.stringify(dataModificados));

    let credentials = username + ":" + password;
    let credenciales64Base = btoa(credentials);
    $.ajax({
        url:'http://localhost:8080/fbtripulantes/tripulantes/guardar-cambios',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        headers: {
            "Authorization": "Basic "+credenciales64Base,
            "X-CSRF-TOKEN":tokenCsrf
        },
        data: JSON.stringify(dataModificados),
        success: function(){
            console.log("datos enviados..");
        }
    })
    table.clear();
    //elemento de la alerta
    let cargaRegistroElem = document.getElementById("carga-registro");
    cargaRegistroElem.style.visibility="visible";
    cargaRegistroElem.style.opacity="1";
});

document.querySelector(".boton_cerrar").addEventListener("click",()=> {
    cerrarAlertaRegistro();
})

document.querySelector(".boton_aceptar").addEventListener("click",()=> {
    cerrarAlertaRegistro();
})

function cerrarAlertaRegistro(){
    let cargaRegistroElem = document.getElementById("carga-registro");
    cargaRegistroElem.style.visibility="hidden";
    cargaRegistroElem.style.opacity="0";
}

getTokenCSRF().then(token=>{
    tokenCsrf = token.token;
   // console.log(tokenCsrf);
    console.log("Token obtenido")
})
.catch(()=>{
    console.log("Error con el token")
})




//Llamada al token CSRF
async function getTokenCSRF(){
        let tokenCsrf=await fetch("http://localhost:8080/fbtripulantes/csrf");
    tokenCsrf = await tokenCsrf.json();
    return tokenCsrf;
}



//Eventos
//Agregar fila
document.getElementById("add-row").addEventListener("click", ()=>{
    table.alter("insert_row_below");

    setTimeout(()=>{
        const rows = table.getData();
        const newRow =  rows[rows.length - 1];
        newRow[5] = '00:00:00';
        newRow[7] = '00:00:00';
        newRow[9] = '00:00:00';

    },0);
    console.log("insertando fila");
});

//Eliminar fila
document.getElementById("remove-row").addEventListener("click", ()=>{
    table.alter("remove_row");
    console.log("eliminar fila");
})






