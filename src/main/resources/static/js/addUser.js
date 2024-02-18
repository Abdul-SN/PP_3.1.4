// кнопка ДОБАВЛЕНИЯ НОВОГО пользователя
$('#add-button').click(function() {
    let modal = $('#add-form');
    let user = {
        firstName: modal.find('#firstName').val(),
        lastName: modal.find('#lastName').val(),
        age: modal.find('#age').val(),
        email: modal.find('#email').val(),
        password: modal.find('#password').val(),
        roles: modal.find('#roles').val().map(roleId => {
            return { id: roleId, roleName: "ROLE_" + modal.find('#roles option:selected')
                    .filter(`[value="${roleId}"]`).text() }
        })
    };

    fetch('/api/v1/admin/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(() => {
            showAllUsers();
            $('#nav-users-tab').tab('show');
            $('#resetBtn').click();
        })
        .catch(error => {
            alert(error.message);
        });
});