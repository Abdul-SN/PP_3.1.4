// модальное окно УДАЛЕНИЯ пользователя
document.getElementById('userDeleteDialog').addEventListener('show.bs.modal', function (event) {
    let button = event.relatedTarget;
    let userId = button.dataset.userId;

    fetch('/api/v1/admin/' + userId)
        .then(response => {
            if (!response.ok) {
                throw new Error(`${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            let modal = this;

            modal.querySelector('#user-id').value = data.id;
            modal.querySelector('#first-name').value = data.firstName;
            modal.querySelector('#last-name').value = data.lastName;
            modal.querySelector('#user-age').value = data.age;
            modal.querySelector('#user-email').value = data.email;
            modal.querySelector('#user-role').value = data.roles.map(role => role.id);
        })
        .catch(error => {
            alert(error.message);
        });

});
// кнопка УДАЛЕНИЯ пользователя
document.getElementById('delete-user-button').addEventListener('click', function() {
    let modal = document.getElementById('userDeleteDialog');
    let userId = modal.querySelector('#user-id').value;

    fetch('/api/v1/admin/delete/' + userId, {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`${response.status}`);
            }
            showAllUsers();
        })
        .catch(error => {
            alert(error.message);
        });
});