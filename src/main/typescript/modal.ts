import $ from 'jquery';
import { deleteBatch, updateBatch } from './batch';
import Modal from 'bootstrap/js/dist/modal';

export function batchDeleteModal() {
    const modalElement = document.getElementById('deleteModal');
    if (!modalElement) return; // nullチェック

    const modal = new Modal(modalElement);
    modal.show();

    $('#deleteButton').off('click').on('click', function () {
        deleteBatch();
        modal.hide();
    });
}

export function batchUpdateModal() {
    const modalElement = document.getElementById('updateModal');
    if (!modalElement) return;

    const modal = new Modal(modalElement);
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
            console.log('value:', targetElem?.value);
            const btn = document.getElementById('updateButton');

            if (targetElem) {
                // チェックボックスの状態に応じて要素の表示/非表示を切り替え
                if (this.checked) {
                    // チェックボックスがオンの場合、対象の要素を表示
                    targetElem.style.display = 'block';
                    targetElem.removeAttribute('disabled');
                    targetElem.addEventListener('change', () => {
                       if (btn) {
                           btn.style.display = targetElem.value.trim() !== '' ? 'inline' : 'none';
                           }
                       });

                } else {
                    // チェックボックスがオフの場合、対象の要素を非表示にし、値をクリア
                    targetElem.style.display = 'none';
                    targetElem.setAttribute('disabled', 'true');
                    targetElem.value = '';
                    if (btn) {
                        btn.style.display = 'none'; // ボタンを非表示
                    }
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