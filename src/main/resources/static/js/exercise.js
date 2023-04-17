function register() {
    const title = $('#title-input').val();
    const url = $('#url-input').val();
    const fitPartName = $('#fitPartName-input').val();
    const youtubeId = $('#youtubeId-input').val();
    const channelName = $('#channelName-input').val();

    $.ajax({
        type: 'POST',
        url: '/api/exercise/register',
        contentType: 'application/json',
        data: JSON.stringify({
            'title': title,
            'url': url,
            'fitPartName': fitPartName,
            'youtubeId': youtubeId,
            'channelName': channelName
        })
    }).done(function (data) {
        alert(`${data.title} 운동이 등록되었습니다.`);
        window.location.replace('/');
    }).fail(function () {
        alert('운동 등록에 실패하였습니다.');
    });
}