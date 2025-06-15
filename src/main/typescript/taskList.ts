import { selectedItems, deleteBatch } from './batch';
import $ from 'jquery';

// チェックボックス表示切替
$('#taskBatchAction').on('click', function () {
    const action = $(this).val();
    $('#result-table-body tr').each(function () {
        const $checkbox = $(this).find('.task-checkbox');
        if (action === '1' || action === '2') {
            $checkbox.css('display', 'inline');
        } else {
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
            deleteBatch();
            break;
        case '2': // 更新
            alert('更新機能はまだ実装されていません。');
            break;
    }
});