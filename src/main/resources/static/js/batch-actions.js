// プルダウンの選択値でチェックボックス列の表示/非表示を切り替え
document.addEventListener('DOMContentLoaded', function() {
    const select = document.getElementById('taskBatchAction'); // プルダウンの要素を取得
    const checkboxCols = document.querySelectorAll('.batch-checkbox-col'); // チェックボックス列の要素を取得
    const checkAll = document.getElementById('batchCheckAll'); // 全選択チェックボックスの要素を取得
    select.addEventListener('change', function() {
        if (select.value === '1' || select.value === '2') { // プルダウンの選択値が"1"または"2"ならチェックボックス列を表示
            checkboxCols.forEach(col => col.style.display = 'table-cell'); // チェックボックス列を表示
        } else {
            checkboxCols.forEach(col => col.style.display = 'none');// チェックボックス列を非表示
            if (checkAll) checkAll.checked = false;// 全選択チェックボックスを非選択にする
            document.querySelectorAll('.batchCheck').forEach(cb => cb.checked = false);// チェックボックスを全て非選択にする
        }
    });
    // 全選択チェックボックス
    if (checkAll) { // 全選択チェックボックスが存在する場合
        checkAll.addEventListener('change', function() { // 全選択チェックボックスの状態変更イベント
            document.querySelectorAll('.batchCheck').forEach(cb => cb.checked = checkAll.checked); // 全てのチェックボックスの状態を全選択チェックボックスに合わせる
        });
    }
    const batchActionButton = document.getElementById('taskBatchActionButton'); // 一括アクションボタンの要素を取得
    batchActionButton.addEventListener('click', function() {// 一括アクションボタンのクリックイベント
        const select = document.getElementById('taskBatchAction');  // プルダウンの要素を再取得
        if (select.value === '1') { // 一括削除
            const checked = Array.from(document.querySelectorAll('.batchCheck:checked')); // チェックされたチェックボックスを取得
            if (checked.length === 0) { // チェックされたチェックボックスがない場合
                alert('削除するタスクを選択してください。'); // アラートを表示
                return;
            }
            if (!confirm('選択したタスクを削除します。よろしいですか？')) return;
            // 選択IDを配列で取得
            const ids = checked.map(cb => cb.value); // チェックされたチェックボックスの値（ID）を配列に変換
            // フォームデータ作成
            const formData = new FormData();
            ids.forEach(id => formData.append('ids', id)); // 選択IDをフォームデータに追加
            fetch('/tasks/batch-delete', { // 一括削除リクエスト
                method: 'POST', // POSTリクエスト
                body: formData,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest' // Ajaxリクエストであることを示すヘッダー
                }
            })
            .then(res => {
                if (res.ok) location.reload();
                else alert('削除に失敗しました');
            });
        } else if (select.value === '2') { // 一括更新
            const checked = Array.from(document.querySelectorAll('.batchCheck:checked')); // チェックされたチェックボックスを取得
            if (checked.length === 0) { // チェックされたチェックボックスがない場合
                alert('更新するタスクを選択してください。');
                return;
            }
            // 選択IDをクエリパラメータで渡して作成画面（例: /tasks/batch-edit）に遷移
            const ids = checked.map(cb => cb.value).join(','); // チェックされたチェックボックスの値（ID）をカンマ区切りで結合
            window.location.href = `/tasks/batch-edit?ids=${encodeURIComponent(ids)}`; // 一括更新画面に遷移
        }
    });
});
