console.log("Hola");
var mensaje = "habia una vez un patito";
var password = "quertyuiquertyui";

var cifrado = CryptoJS.AES.encrypt(mensaje, password);

var descifrado = CryptoJS.AES.decrypt(cifrado, password);

//para que se vea
document.getElementById('demo00').innerHTML = mensaje;
document.getElementById('demo01').innerHTML = cifrado;
document.getElementById('demo02').innerHTML = descifrado;
document.getElementById('demo03').innerHTML = descifrado.toString(Crypto.JS.enc.Utf8);