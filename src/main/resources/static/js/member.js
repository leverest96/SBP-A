function checkEmailDuplication() {
    const email = $('#email-input').val();

    $.ajax({
        type: 'HEAD',
        url: `/api/member/email/${email}`
    }).done(function () {
        alert('중복된 이메일입니다.');
    }).fail(function (jqXHR) {
        if (jqXHR.status === 400) {
            alert('잘못된 이메일 형식입니다.');
        } else if (jqXHR.status === 404) {
            alert('사용 가능한 이메일입니다.');
        }
    });
}

function checkNicknameDuplication() {
    const nickname = $('#nickname-input').val();

    $.ajax({
        type: 'HEAD',
        url: `/api/member/nickname/${nickname}`
    }).done(function () {
        alert('중복된 닉네임입니다.');
    }).fail(function (jqXHR) {
        if (jqXHR.status === 400) {
            alert('잘못된 닉네임 형식입니다.');
        } else if (jqXHR.status === 404) {
            alert('사용 가능한 닉네임입니다.');
        }
    });
}

function sendCode() {
    const email = $('#email-input').val();

    $.ajax({
        type: 'POST',
        url: `/api/member/mailSender`,
        data: {
            'email': email
        }
    }).done(function () {
        alert('메일 전송 완료');
    }).fail(function () {
        alert('메일 전송 실패');
    });
}

function checkVerification() {
    const code = $('#verification-code-input').val();

    $.ajax({
        type: 'POST',
        url: `/api/member/verify`,
        data: {
            'code': code
        }
    }).done(function () {
        alert('이메일 인증 완료');
    }).fail(function () {
        alert('이메일 인증 실패');
    });
}

function register() {
    const email = $('#email-input').val();
    const password = $('#password-input').val();
    const nickname = $('#nickname-input').val();
    const studentId = $('#studentId-input').val();
    const phone = $('#phone-input').val();
    const admin = $('#admin-checkbox').is(':checked');

    $.ajax({
        type: 'POST',
        url: '/api/member/register',
        contentType: 'application/json',
        data: JSON.stringify({
            'email': email,
            'password': password,
            'nickname': nickname,
            'studentId': studentId,
            'phone': phone,
            'admin': admin
        })
    }).done(function (data) {
        alert(`${data.nickname}님 회원가입을 환영합니다.`);
        window.location.replace('/');
    }).fail(function () {
        alert('회원가입을 실패했습니다.');
    });
}

function login() {
    const email = $('#email-input').val();
    const password = $('#password-input').val();

    $.ajax({
        type: 'POST',
        url: '/api/member/login',
        contentType: 'application/json',
        data: JSON.stringify({
            'email': email,
            'password': password
        })
    }).done(function (data) {
        alert(data.nickname + '님 로그인을 성공했습니다.');
        window.location.replace('/');
    }).fail(function () {
        alert('로그인을 실패했습니다.');
    });
}

function logout() {
    $.ajax({
        type: 'POST',
        url: '/api/member/logout'
    }).done(function () {
        alert('로그아웃 완료')
        window.location.replace('/');
    });
}
