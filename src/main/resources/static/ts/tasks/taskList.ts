
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
            alert('更新機能はまだ実装されていません。');
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