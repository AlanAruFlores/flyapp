
/*Obtengo el TOKEN CSRF*/
export async function getTokenCSRF(){
    let tokenCsrf=await fetch("http://localhost:8080/fbtripulantes/csrf");
    tokenCsrf = await tokenCsrf.json();
    return tokenCsrf;
}
