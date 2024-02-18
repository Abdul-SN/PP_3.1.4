// ЗАПОЛНЕНИЕ ТАБЛИЦЫ СО ВСЕМИ ПОЛЬЗОВАТЕЛЯМИ
async function showAllUsers() {
    await fetch('/api/v1/admin/users')
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка выполнения запроса');
            }
            return response.json();
        })
        .then(users => {
            let tableBody = $('#nav-users').find('table').find('tbody');

            // Очищаем текущее содержимое tbody
            tableBody.empty();

            // Проходимся по списку пользователей и добавляем каждого в таблицу
            users.forEach(user => {
                // Создаем строку таблицы
                let row = $('<tr></tr>');

                // Добавляем ячейки с данными пользователя в строку
                row.append($('<td></td>').text(user.id));
                row.append($('<td></td>').text(user.firstName));
                row.append($('<td></td>').text(user.lastName));
                row.append($('<td></td>').text(user.age));
                row.append($('<td></td>').text(user.email));
                row.append($('<td></td>').html(user.roleList.join(' ')));

                // Добавляем кнопки редактирования и удаления
                let editButton = $('<button></button>')
                    .addClass('btn btn-info text-white')
                    .attr('type', 'button')
                    .attr('data-bs-toggle', 'modal')
                    .attr('data-bs-target', '#userEditDialog')
                    .attr('data-user-id', user.id)
                    .text('Edit');
                let deleteButton = $('<button></button>')
                    .addClass('btn btn-danger')
                    .attr('type', 'button')
                    .attr('data-bs-toggle', 'modal')
                    .attr('data-bs-target', '#userDeleteDialog')
                    .attr('data-user-id', user.id)
                    .text('Delete');

                // Добавляем кнопки в строку таблицы
                row.append($('<td></td>').append(editButton));
                row.append($('<td></td>').append(deleteButton));

                // Добавляем созданную строку в tbody таблицы
                tableBody.append(row);
            });
        })
        .catch(error => {
            console.error(error);
        });
}

// ЗАПОЛНЕНИЕ ТАБЛИЦЫ АВТОРИЗОВАННОГО ПОЛЬЗОВАТЕЛЯ
async function getUserInfo() {
    await fetch('/api/v1/admin/getCurrentUser')
        .then(response => {
            return response.json();
        })
        .then(user => {
            $('#navbar-email').text(user.email);
            $('#navbar-roles').text(user.roleList.join(' '));
            // Получаем элемент tbody таблицы
            let tableBody = $('#v-pills-role').find('table').find('tbody');
            // Создаем строку таблицы
            let row = $('<tr></tr>');
            // Добавляем ячейки с данными пользователя в строку
            row.append($('<td></td>').text(user.id));
            row.append($('<td></td>').text(user.firstName));
            row.append($('<td></td>').text(user.lastName));
            row.append($('<td></td>').text(user.age));
            row.append($('<td></td>').text(user.email));
            row.append($('<td></td>').text(user.roleList.join(' ')));

            // Добавляем созданную строку в tbody таблицы
            tableBody.append(row);
        })
        .catch(error => {
            console.error(error);
        });
}
$(document).ready(showAllUsers(), getUserInfo());
