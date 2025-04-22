const path = window.location.pathname;
const btns = Array.from(document.querySelectorAll('#nav-profile>ul>li>a'));
const btns2 = Array.from(document.querySelectorAll('#nav-tripulantes>ul>li>a'));

for (let i = 0; i < btns.length; i++) {
    btns[i].className.replace(" active", "");
}

if(path=="/tripulante/perfil/datos-personales"){
    btns[0].className += " active";
}

if(path=="/tripulante/perfil/seguridad"){
    btns[1].className += " active";
}

if(path=="/tripulante/perfil/privacidad"){
    btns[2].className += " active";
}

if(path=="/tripulante/perfil/admin"){
    btns[3].className += " active";
}

for (let i = 0; i < btns2.length; i++) {
    btns2[i].className.replace(" active", "");
}

if(path=="/lider/vacacionesaprobadas"){
    btns2[0].className += " active";
}

if(path=="/lider/listarPedidos"){
    btns2[1].className += " active";
}

if(path=="/tripulante/perfil/privacidad"){
    btns2[2].className += " active";
}

if(path=="/lider/vestimentas"){
    btns2[3].className += " active";
}

if(path=="/lider/pedidosdiaslibres"){
    btns2[4].className += " active";
}