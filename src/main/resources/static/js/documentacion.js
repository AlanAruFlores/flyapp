async function uploadFile(documentacion_id, parameterName, token) {

    let fileInput = $("#fileInput" + documentacion_id)[0];

    if(fileInput.files.length  === 0){
        alert("Debe seleccionar un archivo para continuar.");
        return false;
    }

    let formData = new FormData();
    formData.append("file", fileInput.files[0]);

    let response = await fetch('/librovuelo/files/upload/documentacion/' + documentacion_id + '?' + parameterName + '=' + token, {
        method: "POST",
        body: formData
    });

    if (response.status === 200) {
        console.log("Adjunto agregado correctamente!");
        location.reload();
    }
}

$(document).ready(function (){

    $("#documentacion-modal-button").click(function(e){
        e.preventDefault();
        let documentacionForm = $("#documentacion-form");
        if(documentacionForm.valid()){
            documentacionForm.submit();
        }
    });

    $(".file-input").on("change", function (e) {
        let files = e.currentTarget.files;
        let uploadButton = $("#uploadButton");

        for (let x in files) {
            let filesize = ((files[x].size/1024)/1024).toFixed(4); // MB

            if (files[x].name !== "item" && typeof files[x].name != "undefined" && filesize > 50) {
                alert("El archivo debe pesar menos de 50 MB.");
                uploadButton.prop('disabled', true);
                return false;
            }
        }
        uploadButton.prop('disabled', false);
        return true;
    });
});