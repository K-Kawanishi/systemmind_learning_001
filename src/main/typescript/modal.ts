import $ from 'jquery';
import { deleteBatch } from './batch';
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