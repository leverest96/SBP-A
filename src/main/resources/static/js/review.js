let reviewUuid = '';

$(function () {
    $.ajax({
        type: 'GET',
        url: `/api/exercise/read/one/${uuid}`
    }).done(function (data) {
        $('#youtube-video').append(`
            <iframe src="${data.url}" style="display: flex; margin: 0 auto; width:600px; height:400px;"></iframe>
        `);
    });

    $.ajax({
        type: 'GET',
        url: `/api/review/readAll/${uuid}?page=${page - 1}&size=${size}`
    }).done(function (data) {
        const reviews = data.reviews;
        const totalPages = data.totalPages;

        reviews.forEach((review, index) => {
            let reviewHtml = `
                <tr>
                  <td>${index + 1 + (page - 1) * 5}</td>
                  <td><a href="#" onclick="readReview('${review.uuid}')" data-bs-toggle="modal" data-bs-target="#detailModal">${review.title}</a></td>
                  <td>${review.nickname}</td>
                  <td>${review.viewCnt}</td>
                  <td>${review.createdDate}</td>
            `;

            if (memberNickname === review.nickname) {
                reviewHtml += `
                    <td><button type="button" class="btn btn-outline-danger delete" onclick="removeReview('${review.uuid}')">삭제</button></td>
                `;
            }

            reviewHtml += `
                </tr>
            `;

            $('#review-table').append(reviewHtml);
        });

        createPagination(page, totalPages, 2);
    });
});

function createPagination(currentPage, totalPages, offset) {
    let firstPage = currentPage - offset;
    let lastPage = currentPage + offset;

    if (firstPage < 1) {
        firstPage = 1;

        if (lastPage < firstPage + offset * 2) {
            lastPage = firstPage + offset * 2;
        }
    }

    if (lastPage > totalPages) {
        lastPage = totalPages;

        if (firstPage > lastPage - offset * 2) {
            firstPage = lastPage - offset * 2;
        }
    }

    if (firstPage < 1) {
        firstPage = 1;
    }

    const paginationList = $('#pagination-list');

    for (let pageNumber = firstPage; pageNumber <= lastPage; pageNumber++) {
        if (pageNumber == currentPage) {
            paginationList.append(`
                <li>
                    <a href="/detail/${uuid}?page=${pageNumber}&size=${size}" class="pagination-link has-background-primary-dark has-text-white">${pageNumber}</a>
                </li>
            `);
        } else {
            paginationList.append(`
                <li>
                    <a href="/detail/${uuid}?page=${pageNumber}&size=${size}" class="pagination-link">${pageNumber}</a>
                </li>
            `);
        }
    }
}

// 리뷰 페이지에서 메인 페이지로 이동하는 함수
function moveMainPage() {
    location.replace("/");
}

function writeComment() {
    if (!reviewable) {
        alert('로그인이 필요합니다.');

        return;
    }

    const commentTitle = document.querySelector('#writeModalInput').value;

    console.log(commentTitle);

    if (!commentTitle) {
        alert('제목을 입력해 주세요.');

        return;
    }

    const commentContent = document.querySelector('#writeModalTextarea').value;

    console.log(commentContent);

    if (!commentContent) {
        alert('내용을 입력해 주세요.');

        return;
    }

    $.ajax({
        type: 'POST',
        url: '/api/review/register',
        contentType: 'application/json',
        data: JSON.stringify({
            'uuid': uuid,
            'title': commentTitle,
            'content': commentContent
        })
    }).done(function () {
        alert('댓글 작성을 성공했습니다.');
        window.location.reload();
    }).fail(function () {
        alert('댓글 작성을 실패했습니다.');
    });
}

function readReview(uuid) {
    $.ajax({
        type: 'PATCH',
        url: `/api/review/cnt/${uuid}`
    })

    $.ajax({
        type: 'GET',
        url: `/api/review/read/${uuid}`
    }).done(function (data) {
        if (memberNickname === data.nickname) {
            const edit = document.querySelector('#edit-button');
            const exit = document.querySelector('#exit-button');

            if (edit || exit) {
                edit.remove();
                exit.remove();
            }

            $('#editor').append(`
                <button id="edit-button" type="button" class="btn btn-outline-primary modify" data-bs-toggle="modal"
                  data-bs-target="#modifyModal">수정</button>
                <button id="exit-button" type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">취소</button>
            `);
        }

        reviewUuid = data.uuid;
        $('.titleHTML').text(data.title);
        $('.contentHTML').text(data.content);
        $('.writerHTML').text(data.nickname);
        $('.dateHTML').text(data.createdDate);
        $('.visitHTML').html(data.viewCnt);
        $('#modifyModalInput').val(data.title);
        $('#modifyModalTextArea').text(data.content);
    }).fail(function () {
        alert('불러오기를 실패했습니다.');
    })
}

function editReview() {
    const commentTitle = document.querySelector('#modifyModalInput').value;

    const commentContent = document.querySelector('#modifyModalTextArea').value;

    $.ajax({
        type: 'PATCH',
        url: `/api/review/${reviewUuid}`,
        contentType: 'application/json',
        data: JSON.stringify({
            'title': commentTitle,
            'content': commentContent
        })
    }).done(function () {
        alert('수정에 성공했습니다.');
        window.location.reload();
    }).fail(function () {
        alert('수정에 실패했습니다.');
    })
}

function removeReview(reviewUuid) {
    $.ajax({
        type: 'DELETE',
        url: `/api/review/${reviewUuid}`
    }).done(function () {
        alert('삭제에 성공했습니다.');
        window.location.reload();
    }).fail(function () {
        alert('삭제에 실패했습니다.');
    })
}