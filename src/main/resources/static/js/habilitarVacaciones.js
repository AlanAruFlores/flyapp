document.addEventListener('DOMContentLoaded', function () {
    const habilitarCheckbox = document.getElementById('habilitar');
    const fechaDesdeInput = document.getElementById('fechaDesde');
    const fechaHastaInput = document.getElementById('fechaHasta');

    function actualizarCampos() {
        if (habilitarCheckbox.checked) {
            fechaDesdeInput.readOnly = false;
            fechaHastaInput.readOnly = false;
        } else {
            fechaDesdeInput.readOnly = true;
            fechaHastaInput.readOnly = true;
        }
    }

    habilitarCheckbox.addEventListener('change', function () {
        actualizarCampos();

        $.ajax({
            type: 'POST',
            url: '/actualizar-estado-checkbox',
            data: { habilitado: habilitarCheckbox.checked }
        });
    });

    actualizarCampos();
});
