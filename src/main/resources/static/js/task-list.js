// 一括削除・チェックボックス制御用JS

document.addEventListener('DOMContentLoaded', function() {
    const selectAll = document.getElementById('selectAll');
    const checkboxes = document.querySelectorAll('.task-checkbox');
    const bulkActionSelect = document.getElementById('bulkActionSelect');
    const bulkActionBtn = document.getElementById('bulkActionBtn');

    // プルダウンとチェックボックスの状態でボタン有効化
    function toggleBulkActionBtn() {
        const anyChecked = [...checkboxes].some(cb => cb.checked);
        const actionSelected = bulkActionSelect.value !== '';
        bulkActionBtn.disabled = !(anyChecked && actionSelected);
    }

    // チェックボックス列の表示制御
    function setCheckboxColumnVisible(visible) {
        document.querySelectorAll('th.checkbox-col').forEach(th => {
            th.style.display = visible ? '' : 'none';
        });
        document.querySelectorAll('td.checkbox-col').forEach(td => {
            td.style.display = visible ? '' : 'none';
        });
    }

    // 初期は非表示
    setCheckboxColumnVisible(false);

    // プルダウン選択時に表示切替
    bulkActionSelect.addEventListener('change', function() {
        const show = bulkActionSelect.value !== '';
        setCheckboxColumnVisible(show);
        // チェックボックスの再取得とイベント再設定
        const checkboxes = document.querySelectorAll('.task-checkbox');
        const selectAll = document.getElementById('selectAll');
        if (selectAll) {
            selectAll.checked = false;
            selectAll.onchange = function() {
                checkboxes.forEach(cb => cb.checked = selectAll.checked);
                toggleBulkActionBtn();
            };
        }
        checkboxes.forEach(cb => {
            cb.checked = false;
            cb.onchange = function() {
                if (selectAll) selectAll.checked = [...checkboxes].every(cb => cb.checked);
                toggleBulkActionBtn();
            };
        });
        toggleBulkActionBtn();
    });

    // 全選択チェックボックス
    if (selectAll) {
        selectAll.addEventListener('change', function() {
            checkboxes.forEach(cb => cb.checked = selectAll.checked);
            toggleBulkActionBtn();
        });
    }

    // 個別チェックボックス
    checkboxes.forEach(cb => {
        cb.addEventListener('change', function() {
            if (selectAll) selectAll.checked = [...checkboxes].every(cb => cb.checked);
            toggleBulkActionBtn();
        });
    });

    // 実行ボタン押下
    bulkActionBtn.addEventListener('click', function() {
        const action = bulkActionSelect.value;
        // プルダウン選択時の最新のチェックボックスを取得
        const checkboxes = document.querySelectorAll('.task-checkbox');
        const ids = [...checkboxes].filter(cb => cb.checked).map(cb => cb.value);
        if (action === 'delete') {
            if (!confirm('選択したタスクを削除しますか？')) return;
            fetch('/tasks/bulk-delete', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                },
                body: JSON.stringify({ ids })
            })
            .then(res => {
                if (res.ok) location.reload();
                else alert('削除に失敗しました');
            });
        }
        // 今後一括更新など追加可能
    });
});
