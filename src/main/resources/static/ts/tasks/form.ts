//ステータス：DONE　ー＞　担当者選択できない制御
$(document).on("change", "#statusInput", (event) => {
    const statusElem = document.getElementById('statusInput') as HTMLInputElement | null;
    if (statusElem?.value === "DONE") {
        $("#pullDown").hide();
    } else {
        $("#pullDown").show();
    }
});
