import $ from 'jquery';

// チェックしたタスクのリストを返す
export const selectedItems = (): (string | undefined)[] => {
    return $("#result-table-body input:checked")
        .map(function () { return $(this).data("n"); })
        .get();
}

export function deleteBatch() {
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
