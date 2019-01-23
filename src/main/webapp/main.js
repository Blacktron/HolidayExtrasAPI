var users = [];

$('#addUserBtn').on('click', function(e) {
    e.preventDefault();
    var id = $('#userID').val();
    var email = $('#userEmail').val();
    var firstName = $('#givenName').val();
    var lastName = $('#familyName').val();
    var created = new Date();

    var isUserDataValid = validateUserData (id, email, firstName, lastName);
    if (isUserDataValid === true) {
        var newUser = {
            userID: id,
            userEmail: email,
            userGivenName: firstName,
            userFamilyName: lastName,
            created: created
        };

        $.ajax({
            method: "POST",
            url: "/HolidayExtrasAPI/myRestAPI/users",
            data: JSON.stringify(newUser),
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function(response) {
                alert(response.Status);
            },
            error: function(response) {
                alert(response.Error);
            }
        });
    } else {
        alert('Please fill all fields!');
    }



    // var isUserCreated = createUser(id, email, firstName, lastName, created);
    // isUserCreated ? alert('User created successfully!') : alert('User with the same ID already exists!');

    resetUserForm();
});

$('#showAllUsers').on('click', function () {
    $.ajax({
        method: 'GET',
        url: "/HolidayExtrasAPI/myRestAPI/users",
        success: function(response) {
            displayResult(response);
        },
        error: function(response) {
            alert(response.Error);
        }
    });
});

$('#editUserBtn').on('click', function(e) {
    e.preventDefault();
    var userID = $('#userID').val();
    var userEmail = $('#userEmail').val();
    var userFirstName = $('#givenName').val();
    var userLastName = $('#familyName').val();
    var editUrl = '/HolidayExtrasAPI/myRestAPI/users/' + userID;

    var newUser = {
        userEmail: userEmail,
        userGivenName: userFirstName,
        userFamilyName: userLastName
    };

    $.ajax({
        method: 'POST',
        url: editUrl,
        dataType: 'json',
        data: JSON.stringify(newUser),
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            alert(response.Status);
        },
        error: function (response) {
            alert(response);
        }
    });

    resetUserForm();
});

$('#resetUserBtn').on('click', function() {
    resetUserForm();
});

$('#searchForUser').on('click', function() {
    var searchStr = $('#searchUser').val().toLowerCase();
    var searchUrl = '/HolidayExtrasAPI/myRestAPI/users/' + searchStr;

    if (searchStr.length > 0) {
        $.ajax({
            method: 'GET',
            url: searchUrl,
            success: function(response) {
                displayResult(response);
            },
            error: function(response) {
                alert(response);
            }
        });
    }
});

$('table').on('click', 'tr button.delete-button', function() {
    var userID = $(this).closest('tr').find('.table-row-id').text();
    var deleteUrl = '/HolidayExtrasAPI/myRestAPI/users/' + userID;
    $(this).closest('tr').remove();

    $.ajax({
        method: 'DELETE',
        url: deleteUrl,
        success: function (response) {
            alert(response.Status);
        },
        error: function (response) {
            alert(response);
        }
    });
});

$('table').on('click', 'tr button.edit-button', function() {
    var userID = $(this).closest('tr').find('.table-row-id').text();
    var userEmail = $(this).closest('tr').find('.table-row-email').text();
    var userFirstName = $(this).closest('tr').find('.table-row-first-name').text();
    var userLastName = $(this).closest('tr').find('.table-row-last-name').text();

    $('#userID').val(userID).prop('disabled', 'disabled');
    $('#userEmail').val(userEmail);
    $('#givenName').val(userFirstName);
    $('#familyName').val(userLastName);
});

/**
 * @function
 * @description     Clears the data from the form.
 */
function resetUserForm() {
    $('#addUser')[0].reset();
    $('#userID').removeAttr('disabled');
}

/**
 * @function
 * @description     Displays the user data in the table.
 * @param users     The array which holds the users and the date related to them.
 */
function displayResult(users) {
    var userTable = $('#allUsersData');
    var editBtn = "<button class=\"btn btn-success edit-button\" type=\"button\">Edit</button>";
    var deleteBtn = "<button class=\"btn btn-danger delete-button\" type=\"button\">Delete</button>";
    $('#allUsers').show();

    // Remove previously displayed data.
    userTable.find('tr:gt(0)').remove();

    for (var i = 0; i < users.length; i++) {
        var userData = users[i];
        var userDataRow = "<tr><td class='table-row-id'>" + userData.id + "</td><td class='table-row-email'>" + userData.email +
            "</td><td class='table-row-first-name'>" + userData.givenName + "</td><td class='table-row-last-name'>" + userData.familyName +
            "</td><td class='table-row-created'>" + userData.date + "</td><td>" + editBtn + deleteBtn + "</td></tr>";

        userTable.append(userDataRow);
    }
}

/**
 * @function
 * @description         Validates if all data is provided
 * @param id            The ID of the user.
 * @param email         The email of the user.
 * @param firstName     The first name.
 * @param lastName      The last name.
 * @returns {boolean}   True if all data is provided, false otherwise.
 */
function validateUserData (id, email, firstName, lastName) {
    if (id === '' || email === '' || firstName === '' || lastName === '') {
        return false;
    }

    return true;
}

/**
 * @function
 * @description     Unit tests user creation.
 */
function testCreateUser(userID, userEmail, userFirstName, userLastName) {
    var created = new Date();
    var isTestUserCreated = createUser(userID, userEmail, userFirstName, userLastName, created);
    isTestUserCreated ? console.log('Success') : console.log('Fail');
}
//testCreateUser('vbutanski', 'viktor.butanski@gmail.com', 'Viktor', 'Viktor', 'Butanski');