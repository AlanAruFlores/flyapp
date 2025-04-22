
const provinciaSelect = document.getElementById("provincia");
const localidadSelect = document.getElementById("localidad");

async function  loadLocalities(provinceId, userLocalityId) {
    let tokenCsrf = await fetch("http://localhost:8080/fbtripulantes/csrf");
    tokenCsrf = await tokenCsrf.json();

    //console.log("TOKEN: "+JSON.stringify(tokenCsrf));
    //console.log(tokenCsrf.token);


    fetch(`/fbtripulantes/listarLocalidades/${provinceId}`, {
        method: "POST",
        headers: {
            "X-CSRF-TOKEN": tokenCsrf.token
        }
    })
        .then((response) => response.json())
        .then((data) => {
            localidadSelect.innerHTML = "";
            data.forEach((localidad) => {
                const option = document.createElement("option");
                option.value = localidad.id;
                option.textContent = localidad.nombre;

                if (userLocalityId && localidad.id === userLocalityId) {
                    option.selected = true;
                }

                localidadSelect.appendChild(option);
            });
        })
        .catch((error) => {
            console.error("Error loading localities:", error);
        });
}

document.addEventListener("DOMContentLoaded", () => {
    fetch(`/fbtripulantes/obtenerLocalidadUsuario`)
        .then((response) => response.json())
        .then((userLocalidad) => {
            const selectedProvinceId = provinciaSelect.value;
            const userLocalityId = userLocalidad.id;

            if (selectedProvinceId !== "0") {
                loadLocalities(selectedProvinceId, userLocalityId);
            }
        })
        .catch((error) => {
            console.error("Error loading user's locality:", error);
        });
});


provinciaSelect.addEventListener("change", () => {
    const selectedProvinceId = provinciaSelect.value;
    const userLocalityId = localidadSelect.value;

    if (selectedProvinceId !== "0") {
        loadLocalities(selectedProvinceId, userLocalityId);
    } else {
        clearLocalities();
    }
});

function clearLocalities() {
    localidadSelect.innerHTML = '<option value="0">Selecciona tu localidad</option>';
}
