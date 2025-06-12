console.log("Hola");
var mensaje = "habia una vez un patito";
var password = "quertyuiquertyui";

var cifrado = CryptoJS.AES.encrypt(mensaje, password);

var descifrado = CryptoJS.AES.decrypt(cifrado, password);

//para que se vea
document.getElementById('demo00').innerHTML = mensaje;
document.getElementById('demo01').innerHTML = cifrado;
document.getElementById('demo02').innerHTML = descifrado;
document.getElementById('demo03').innerHTML = descifrado.toString(CryptoJS.enc.Utf8);

document.getElementById('ejemplo').style.display = "none";

function mostrarSeccion(id) {
    document.getElementById("cifrar").style.display = "none";
    document.getElementById("descifrar").style.display = "none";
    document.getElementById(id).style.display = "block";
}

function validarClave(clave) {
    return clave.length >= 16;
}

function cifrarMensaje() {
    const mensaje = document.getElementById("mensajeACifrar").value;
    const clave = document.getElementById("claveCifrar").value;

    if (!validarClave(clave)) {
        alert("La clave debe tener al menos 16 caracteres.");
        return;
    }

    const cifrado = CryptoJS.AES.encrypt(mensaje, clave).toString();
    const blob = new Blob([cifrado], { type: "text/plain;charset=utf-8" });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "mensaje_cifrado.txt";
    link.click();
}

function descifrarMensaje() {
    const archivo = document.getElementById("archivoCifrado").files[0];
    const clave = document.getElementById("claveDescifrar").value;

    if (!archivo) {
        alert("Selecciona un archivo cifrado.");
        return;
    }

    if (!validarClave(clave)) {
        alert("La clave debe tener al menos 8 caracteres.");
        return;
    }

    const lector = new FileReader();
    lector.onload = function (e) {
        try {
            const contenido = e.target.result;
            const descifrado = CryptoJS.AES.decrypt(contenido, clave);
            const textoPlano = descifrado.toString(CryptoJS.enc.Utf8);

            if (!textoPlano) {
                throw new Error("Clave incorrecta o archivo inválido.");
            }

            document.getElementById("mensajeDescifrado").value = textoPlano;
        } catch (err) {
            alert("Error al descifrar: " + err.message);
        }
    };
    lector.readAsText(archivo);
}