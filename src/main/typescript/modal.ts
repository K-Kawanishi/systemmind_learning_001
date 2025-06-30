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
    if (statusElem) {
        statusElem.style.display = 'none';
    }
    const priorityElem = document.getElementById('priority');
    if (priorityElem) {
        priorityElem.style.display = 'none';
    }


    document.getElementById('statusCheckbox')?.addEventListener('change', function(this: HTMLInputElement) {
        const statusElem = document.getElementById('status');
        if (statusElem) {
            statusElem.style.display = this.checked ? 'inline' : 'none';
        }
    });
    document.getElementById('priorityCheckbox')?.addEventListener('change', function(this: HTMLInputElement) {
        const priorityElem = document.getElementById('priority');
        if (priorityElem) {
            priorityElem.style.display = this.checked ? 'inline' : 'none';
        }
    });

    $('#updateButton').off('click').on('click', function () {
        updateBatch();
        modal.hide();
    });
}