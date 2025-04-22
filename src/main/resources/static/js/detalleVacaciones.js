/*
let firstInputA= document.querySelector("#floatingSelectFirstDateA");
let firstInputB= document.querySelector("#floatingSelectFirstDateB");
let firstSearch=document.querySelector("#firstSearch");

let secondInputA = document.querySelector("#floatingSelectSecondDateA");
let secondInputB = document.querySelector("#floatingSelectSecondDateB");
let secondSearch=document.querySelector("#secondSearch");

let thirdInputA = document.querySelector("#floatingSelectThirdDateA");
let thirdInputB = document.querySelector("#floatingSelectThirdDateB");
let thirdSearch=document.querySelector("#thirdSearch");

let btnForfirstA= document.querySelector("#aproveFirstDateA");
let btnForsecondA= document.querySelector("#aproveSecondDateA");
let btnForthirdA= document.querySelector("#aproveThirdDateA");

let btnForfirstB= document.querySelector("#aproveFirstDateB");
let btnForsecondB= document.querySelector("#aproveSecondDateB");
let btnForthirdB= document.querySelector("#aproveThirdDateB");

let disabledFalse = (inputA,inputB,btnDisabled)=>{
    if (inputA.value ==="" && inputB.value ===""){
        btnDisabled.disabled=false;
    }
}

disabledFalse(firstInputA,firstInputB,firstSearch);
disabledFalse(secondInputA,secondInputB,secondSearch);
disabledFalse(thirdInputA,thirdInputB,thirdSearch);

let disabledBtnForInput = (input,btn)=>{
    if (input.value===""){
        btn.disabled=true;
    }
}
disabledBtnForInput(firstInputA,btnForfirstA);
disabledBtnForInput(secondInputA,btnForsecondA);
disabledBtnForInput(thirdInputA,btnForthirdA);
disabledBtnForInput(firstInputB,btnForfirstB);
disabledBtnForInput(secondInputB,btnForsecondB);
disabledBtnForInput(thirdInputB,btnForthirdB);

let aprovedVacation1= document.getElementById("floatinAgassignedDateA");
let aprovedVacation2= document.getElementById("floatinAgassignedDateB");
let aprovedVacation3= document.getElementById("floatinAgassignedDateC");

//fechas hasta (hidden)
let hidennFirstDateA=document.getElementById("hidennFirstDateA");
let hidennSecondDateA=document.getElementById("hidennSecondDateA");
let hidennThirdDateA=document.getElementById("hidennThirdDateA");
let hidennFirstDateB=document.getElementById("hidennFirstDateB");
let hidennSecondDateB=document.getElementById("hidennSecondDateB");
let hidennThirdDateB=document.getElementById("hidennThirdDateB");


let aprovedVacation1final = document.getElementById("assignedDateALimit");
let aprovedVacation2final = document.getElementById("assignedDateBLimit");
let aprovedVacation3final = document.getElementById("assignedDateCLimit");


function getValueInput(id){
    switch (id) {
        case btnForfirstA.getAttribute("id"):
            aprovedVacation1.value=firstInputA.value;
            aprovedVacation1final.value=hidennFirstDateA.value;
            break;
        case btnForsecondA.getAttribute("id"):
            aprovedVacation2.value=secondInputA.value;
            aprovedVacation2final.value=hidennSecondDateA.value;
            break;
        case btnForthirdA.getAttribute("id"):
            aprovedVacation3.value=thirdInputA.value;
            aprovedVacation3final.value=hidennThirdDateA.value;
            break;
        case btnForfirstB.getAttribute("id"):
            aprovedVacation1.value=firstInputB.value;
            aprovedVacation1final.value=hidennFirstDateB.value;
            break;
        case btnForsecondB.getAttribute("id"):
            aprovedVacation2.value=secondInputB.value;
            aprovedVacation2final.value=hidennSecondDateB.value;
            break;
        case btnForthirdB.getAttribute("id"):
            aprovedVacation3.value=thirdInputB.value;
            aprovedVacation3final.value=hidennThirdDateB.value;
            break;
    }
}

*/
