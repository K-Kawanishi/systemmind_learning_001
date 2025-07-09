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
    if(Btn){
        Btn.style.display = 'none';
        }
    if (statusElem) {
        statusElem.style.display = 'none';
    }
    const priorityElem = document.getElementById('priority');
    if (priorityElem) {
        priorityElem.style.display = 'none';
    }


    function handleCheckboxToggle(
        checkboxId: string,
        targetElementId: string
    ) {
        const checkbox = document.getElementById(checkboxId) as HTMLInputElement | null;

        checkbox?.addEventListener('change', function () {
            const targetElem = document.getElementById(targetElementId);
            const btn = document.getElementById('updateButton');

            if (targetElem) {
                targetElem.style.display = this.checked ? 'inline' : 'none';
            }

            const statusChecked = (document.getElementById('statusCheckbox') as HTMLInputElement)?.checked;
            const priorityChecked = (document.getElementById('priorityCheckbox') as HTMLInputElement)?.checked;

            if (btn) {
                btn.style.display = (statusChecked || priorityChecked) ? 'inline' : 'none';
            }
        });
    }

    handleCheckboxToggle('statusCheckbox', 'status');
    handleCheckboxToggle('priorityCheckbox', 'priority');



    $('#updateButton').off('click').on('click', function () {
        updateBatch();
        modal.hide();
    });
}