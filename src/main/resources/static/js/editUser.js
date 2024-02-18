
// модальное окно РЕДАКТИРОВАНИЯ пользователя
$('#userEditDialog').on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget); // Button that triggered the modal
    let userId = button.data('user-id'); // Extract info from data-* attributes


    fetch('/api/v1/admin/' + userId)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            let modal = $(this);
            modal.find('#user-id').val(data.id);
            modal.find('#first-name').val(data.firstName);
            modal.find('#last-name').val(data.lastName);
            modal.find('#user-age').val(data.age);
            modal.find('#user-email').val(data.email);
            modal.find('#user-password').val();
            modal.find('#user-role').val(data.roles.map(role => role.id));
        })
        .catch(error => {
            alert(error.message);
        });

});
// кнопка СОХРАНЕНИЯ изменений
$('#save-user-button').click(function () {
    let modal = $('#userEditDialog');
    let user = {
        id: modal.find('#user-id').val(),
        firstName: modal.find('#first-name').val(),
        lastName: modal.find('#last-name').val(),
        age: modal.find('#user-age').val(),
        email: modal.find('#user-email').val(),
        password: modal.find('#user-password').val(),
        roles: modal.find('#user-role').val().map(roleId => {
            return {
                id: roleId, roleName: "ROLE_" + modal.find('#user-role option:selected')
                    .filter(`[value="${roleId}"]`).text()
            }
        })
    };

    fetch('/api/v1/admin/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // or whatever you want to do with the response
        })
        .then(() => {
            showAllUsers();
        })
        .catch(error => {
            alert(error.message);
        });
});