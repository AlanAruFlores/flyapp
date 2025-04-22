document.addEventListener("keyup", e=>{

    if (e.target.matches("#buscador")){

        if (e.key ==="Escape")e.target.value = ""

        document.querySelectorAll(".tripulantes").forEach(tripulante =>{

            tripulante.textContent.toLowerCase().includes(e.target.value.toLowerCase())
                ? tripulante.classList.remove("filtro")
                : tripulante.classList.add("filtro")

            })
    }
})

/*$("#danger-alert").fadeTo(4000, 500).slideUp(500, function(){
    $("#danger-alert").slideUp(500);
});*/

/*$('.buscador').on('keyup', function(event) { // Fired on 'keyup' event

    if($('.tripulantes').children().length === 0) { // Checking if list is empty

        $('.not-found').css('display', 'block'); // Display the Not Found message

    } else {

        $('.not-found').css('display', 'none'); // Hide the Not Found message

    }
});*/