if (document.readyState == 'loading') {
    document.addEventListener('DOMContentLoaded', ready)
} else {
    ready()
}

function ready(){
    windowSizeListener();
}

// Defining event listener function
function windowSizeListener(){
    // Get width of the window excluding scrollbars
    let w = document.documentElement.clientWidth;

    if (w < 992 && document.getElementsByClassName('nav-btns')[1].innerHTML != '') {
        removeButtons()
        replaceButtons(1)
        replaceLogout(1)
        document.getElementsByClassName('nav-btns')[1].innerHTML = '';
    }

    if (w > 991 && document.getElementsByClassName('nav-btns')[0].innerHTML != '') {
        removeButtons()
        replaceButtons(2)
        replaceLogout(2)
        document.getElementsByClassName('nav-btns')[0].innerHTML = '';
    }

    if (w < 576) {
        replaceIsologoIsotipo(1);
    }

    if (w > 575) {
        replaceIsologoIsotipo(2);
    }

}

// Attaching the event listener function to window's resize event
window.addEventListener("resize", windowSizeListener);

function removeButtons(){
    let buttons = document.getElementsByClassName('nav-btn')
        for (let i = 0; i < buttons.length; i++) {
            buttons[i].remove()
        }
}

function replaceButtons(number){
    let buttonsContent = `
                        <div class="btn-group">
                            <button class="btn nav-btn position-relative" type="button" id="defaultDropdown" data-bs-toggle="dropdown" data-bs-auto-close="true" aria-expanded="false">
                                <i class="bi bi-bell nav-icon"></i>
                                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                99+
                                <span class="visually-hidden">New alerts</span>
                                </span>
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="defaultDropdown">
                                <li><a class="dropdown-item" href="#">Notification One</a></li>
                                <li><a class="dropdown-item" href="#">Notification Two</a></li>
                                <li><a class="dropdown-item" href="#">Notification Three</a></li>
                            </ul>
                        </div>
                        <a href="/tripulante/perfil/datos-personales" type="button" class="btn nav-btn position-relative">
                            <i class="bi bi-person-circle nav-icon"></i>
                            </span>
                        </a>
    `

    if(number==1){
        let buttonsReplacementDiv = document.getElementsByClassName('nav-btns')[0]
        buttonsReplacementDiv.innerHTML = buttonsContent
    }
    if(number==2){
        let buttonsReplacementDiv = document.getElementsByClassName('nav-btns')[1]
        buttonsReplacementDiv.innerHTML = buttonsContent
    }
}

function replaceLogout(number){

    if (number==1){
        let logout = document.getElementsByClassName('logout')[1].innerHTML
        document.getElementsByClassName('logout-child')[0].remove()
        document.getElementsByClassName('logout')[0].innerHTML = logout
    }
    if (number==2){
        let logout = document.getElementsByClassName('logout')[0].innerHTML
        document.getElementsByClassName('logout-child')[0].remove()
        document.getElementsByClassName('logout')[1].innerHTML = logout
    }
}

function replaceIsologoIsotipo(number){
    let iso = document.getElementById("brand-image");
    if (number==1){
        //iso.src=[[@/img/isotipo.png]];
      //  iso.width=30;
    }
    if (number==2){
        //iso.src=[[@/img/isologo.svg]];
      //  iso.width=200;
    }
}




