$(function () {
    $.ajax({
        type: 'GET',
        url: `/api/exercise/read?page=${page - 1}&size=${size}`
    }).done(function (data) {
        const exercises = data.exercises;
        const totalPages = data.totalPages;

        exercises.forEach(exercise => {
            let exerciseHtml = `
                <div class="box is-shadowless mb-4">
                    <div class="is-flex is-justify-content-space-between mb-3">
                        <div class="is-flex is-flex-direction-row">
                            <p>
                                <b>${exercise.title}</b>
                            </p>
                            <p>&nbsp;·&nbsp;</p>
                            <p>${exercise.url}</p>
                        </div>
                    </div>
                </div>
            `;

            $('#exercise').append(exerciseHtml);
        });

        createPagination(page, totalPages, 2);
    });
});

// 메인 페이지에서 리뷰 페이지로 전환시, 사용자 선택 정보를 제공하는 함수 
function movePage(data) {
    location.replace(data.url);
}

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
                    <a href="/til/${uuid}?page=${pageNumber}&size=${size}" class="pagination-link has-background-primary-dark has-text-white">${pageNumber}</a>
                </li>
            `);
        } else {
            paginationList.append(`
                <li>
                    <a href="/til/${uuid}?page=${pageNumber}&size=${size}" class="pagination-link">${pageNumber}</a>
                </li>
            `);
        }
    }
}