import { selectedItems } from './batch';
import {batchDeleteModal} from './modal';
import $ from 'jquery';

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