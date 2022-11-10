
(presentacion=()=>{
    let $displeyinicio = document.querySelector(".displayinicio")  
    let $box = document.querySelector(".boxdispley")
    
    setTimeout(() => {
        $displeyinicio.classList="ocultar";
        $box.innerHTML ='<h1 class=" displeyBienvenida col-12  mb-4 ">Bienvenido a <span class=" d-flex justify-content-center text-warning">EGG-ALL</span> </h1>'
    }, 12000);

})();