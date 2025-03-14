//obtener los datos para cifrar
const desplazamiento = document.getElementById('desplazamiento');
const texto = document.getElementById('texto');
const textoCifrado = document.getElementById('cifrado');
console.log("Hola mundo");
//vamos a crear una funcion que se encargue del algoritmo de cesar
function cifrado() {

    //obtener el texto que se ingresó para cifrar
    const textoIngresado = texto.value;
    //debo de obtener cada caracter de la cadena y validar unicamente caracteres
    
    textoCifrado.value = textoIngresado.split('').map( c => {
        let mayus = (c === c.toUpperCase()) ? true: false;
        let valorEntero = c.toLowerCase().charCodeAt(0);
        

        if(valorEntero >=97 && valorEntero <= 122) {
            const valorDesplazamiento = parseInt(desplazamiento.value)
            if (valorEntero + valorDesplazamiento > 122) {
                valorEntero = 97 + (valorEntero - 122) + valorDesplazamiento - 1;
            
            } else {
                valorEntero = valorEntero + valorDesplazamiento;
            }
        }

        //debo juntar los elementos para la cadena de cifrado
        let cifrado = String.fromCharCode(valorEntero);
        return mayus ? cifrado.toUpperCase() : cifrado;
    }).join('');
}

texto.addEventListener('keyup', cifrado);
desplazamiento.addEventListener('change', cifrado);
