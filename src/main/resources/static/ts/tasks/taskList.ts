
// チェックしたタスクのリストを返す
 const selectedItems = (): (string | undefined)[] => {
    return $("#result-table-body input:checked")
        .map(function () { return $(this).data("n"); })
        .get();
}

 function deleteBatch() {
    const ids = selectedItems();
    if (ids.length === 0) {
        alert("タスクが選択されていません。");
        return ;
    }

    return $.ajax( {
        url: "/tasks/deleteBatch",
        type: "post",
        data: $.param({ids : ids},true)
    })
    .done(function (param) {
      // 通信成功時の処理
    }).fail(function () {
      // 通信失敗時の処理
    }).always(function () {
      // 成功や失敗にかかわらず常に実行する処理
      location.reload();
    });
}

 function updateBatch() {
    const ids = selectedItems();
    const status = $("#status").val();
    const priority = $("#priority").val();
    if (ids.length === 0) {
        alert("タスクが選択されていません。");
        return ;
    }

    return $.ajax( {
        url: "/tasks/updateBatch",
        type: "post",
        data: $.param({ids : ids,status : status, priority : priority},true)
    })
    .done(function (param) {
      // 通信成功時の処理
    }).fail(function () {
      // 通信失敗時の処理
    }).always(function () {
      // 成功や失敗にかかわらず常に実行する処理
      location.reload();
    });
}



// チェックボックスと実行ボタン表示切替
$('#taskBatchAction').on('change', function () {
    const action = $(this).val();
    $('#result-table-body tr').each(function () {
        const buton = $('#taskBatchActionButton');
        const $checkbox = $(this).find('.task-checkbox');
        if (action != 0) {
            buton.css('display', 'inline');
            $checkbox.css('display', 'inline');
            $checkbox.prop('checked', false); // チェックを外す
        } else {
            buton.css('display', 'none');
            $checkbox.css('display', 'none');
        }
    });
});

$('#taskBatchActionButton').on('click', function () {
    const action = $('#taskBatchAction').val();
    const ids = selectedItems();
    if (ids.length === 0) {
        alert('タスクが選択されていません。');
        return;
    }
    switch (action) {
        case '1': // 削除
             batchDeleteModal();
            break;
        case '2': // 更新
            batchUpdateModal();
            break;
    }
});

function batchDeleteModal() {
    const modalElement = document.getElementById('deleteModal');
    if (!modalElement) return; // nullチェック

     const modal = new bootstrap.Modal(modalElement);
      modal.show();
    $('#deleteButton').off('click').on('click', function () {
        deleteBatch();
        modal.hide();
    });
}

function batchUpdateModal() {
    const modalElement = document.getElementById('updateModal');
    if (!modalElement) return; // nullチェック
     const modal = new bootstrap.Modal(modalElement);
      modal.show();

    //初期状態では非表示
    const statusElem = document.getElementById('status');
    const Btn = document.getElementById('updateButton');
    const priorityElem = document.getElementById('priority');
    if(Btn){
        Btn.style.display = 'none';
        }
    if (statusElem) {
        statusElem.style.display = 'none';
    }
    if (priorityElem) {
        priorityElem.style.display = 'none';
    }

    function handleCheckboxToggle(
        checkboxId: string,
        targetElementId: string
    ) {
        const checkbox = document.getElementById(checkboxId) as HTMLInputElement | null;

        checkbox?.addEventListener('change', function () {
            const targetElem = document.getElementById(targetElementId) as HTMLInputElement | null;

            if (targetElem) {
                // チェックボックスの状態に応じて要素の表示/非表示を切り替え
                if (this.checked) {
                    // チェックボックスがオンの場合、対象の要素を表示
                    targetElem.style.display = 'block';
                    targetElem.removeAttribute('disabled');
                    targetElem.addEventListener('change', () => {
                    changeValue();
                    });
                } else {
                    // チェックボックスがオフの場合、対象の要素を非表示にし、値をクリア
                    targetElem.style.display = 'none';
                    targetElem.setAttribute('disabled', 'true');
                    targetElem.value = '';
                    checkBothUnchecked();
                }
            }
        }
        );
    }

    handleCheckboxToggle('statusCheckbox', 'status');
    handleCheckboxToggle('priorityCheckbox', 'priority');

    $('#updateButton').off('click').on('click', function () {
        updateBatch();
        modal.hide();
    });
}
function checkBothUnchecked() {
    const statusCheckbox = document.getElementById('statusCheckbox') as HTMLInputElement | null;
    const priorityCheckbox = document.getElementById('priorityCheckbox') as HTMLInputElement | null;
    const statusElem = document.getElementById('status') as HTMLInputElement | null;
    const priorityElem = document.getElementById('priority') as HTMLInputElement | null;
    const Btn = document.getElementById('updateButton');
    if (Btn && statusCheckbox && priorityCheckbox && statusElem && priorityElem) {
        if (!statusCheckbox.checked && !priorityCheckbox.checked) {
            Btn.style.display = 'none';
        }else {
            if (statusCheckbox.checked && statusElem.value.trim() === '') {
                Btn.style.display = 'none';
                }else if (priorityCheckbox.checked && priorityElem.value.trim() === '') {
                Btn.style.display = 'none';
                }
            }
        }
    }
function  changeValue(){
    const statusElem = document.getElementById('status') as HTMLInputElement | null;
    const priorityElem = document.getElementById('priority') as HTMLInputElement | null;
    const Btn = document.getElementById('updateButton');
    if (statusElem && priorityElem && Btn) {
        if (statusElem.value.trim() === '' && priorityElem.value.trim() === '') {
            Btn.style.display = 'none';
        } else {
            Btn.style.display = 'inline';
        }
    }
}
