//ステータス：DONE　ー＞　担当者選択できない制御
$(document).on("change", "#statusInput", (event) => {
    const statusElem = document.getElementById('statusInput') as HTMLInputElement | null;
    if (statusElem?.value === "DONE") {
        $("#pullDown").hide();
    } else {
        $("#pullDown").show();
    }
});

//担当者選択したら、ステータスをDOINGに変更
$(document).on("change", "#operatorId", (event) => {
    const id = (event.target as HTMLInputElement).value;
    if(id !== "0"){
        $("#statusInput").val("DOING");
        }else{
            $("#statusInput").val("TODO");}
    });