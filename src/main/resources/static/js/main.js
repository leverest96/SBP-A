$(function () {
    $.ajax({
        type: 'GET',
        url: '/api/exercise/read'
    }).done(function (data) {
        const exercises = data.exercises;

        exercises.forEach((exercise, index) => {
            if (index === 0) {
                $('#carousel-inner').append(`
                    <div class="carousel-item active" style="width: 100%; overflow: hidden">
                      <div class="container container-top">
                        <div class="row" id="row-${Math.floor(index / 3)}">
                        
                        </div>
                      </div>
                    </div>
                `);
            } else if (index % 3 === 0 && index !== 0) {
                $('#carousel-inner').append(`
                    <div class="carousel-item" style="width: 100%; overflow: hidden">
                      <div class="container container-bottom">
                        <div class="row" id="row-${Math.floor(index / 3)}">
                        
                        </div>
                      </div>
                    </div>
                `);
            }
        });

        exercises.forEach((exercise, index) => {
            $(`#row-${Math.floor(index / 3)}`).append(`
                <div class="col">
                  <div class="card">
                    <iframe src="${exercise.url}"></iframe>
                    <div class="card-body" onclick="location.href ='detail/${exercise.uuid}?page=1&size=5'">
                      <h5 class="card-title">${exercise.title}</h5>
                      <span class="card-text" style="color:#0c4da2; font-weight: bold;">${exercise.fitPartName}</span>
                      <i class="bi bi-eye" style="float: right; margin-right:3%;"> ${exercise.viewCnt}</i>
                    </div>
                  </div>
                </div>
            `);
        });
    }).fail(function (xhr, status, error) {
        console.error('AJAX request failed:', status, error);
    });
});

function select(fitPartName) {
    $.ajax({
        type: 'GET',
        url: `/api/exercise/read/${fitPartName}`
    }).done(function (data) {
        const exercises = data.exercises;

        const element = document.querySelector('.variable');

        if (element) {
            element.remove();
        }

        if (exercises === null) {
            $('#carousel-inner-part').append(`
                    <div class="variable carousel-item" style="width: 100%; overflow: hidden">
                      <div class="container container-bottom">
                        <div class="row">
                          <div class="col">
                            <div class="card">
                              <div class="card-body">
                                <h1 class="card-title">아직 등록된 영상이 없습니다.</h1>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                `);
        }

        exercises.forEach((exercise, index) => {
            if (index === 0) {
                $('#carousel-inner-part').append(`
                    <div class="variable carousel-item active" style="width: 100%; overflow: hidden">
                      <div class="container container-top">
                        <div class="row" id="part-row-${Math.floor(index / 2)}">
                        
                        </div>
                      </div>
                    </div>
                `);
            } else if (index % 2 === 0 && index !== 0) {
                $('#carousel-inner-part').append(`
                    <div class="variable carousel-item" style="width: 100%; overflow: hidden">
                      <div class="container container-bottom">
                        <div class="row" id="part-row-${Math.floor(index / 2)}">
                        
                        </div>
                      </div>
                    </div>
                `);
            }
        });

        exercises.forEach((exercise, index) => {
            $(`#part-row-${Math.floor(index / 2)}`).append(`
                <div class="col">
                  <div class="card">
                    <iframe src="${exercise.url}"></iframe>
                    <div class="card-body" onclick="location.href ='detail/${exercise.uuid}?page=1&size=5'">
                      <h5 class="card-title">${exercise.title}</h5>
                      <span class="card-text" style="color:#0c4da2; font-weight: bold;">${exercise.fitPartName}</span>
                      <i class="bi bi-eye" style="float: right; margin-right:3%;"> ${exercise.viewCnt}</i>
                    </div>
                  </div>
                </div>
            `);
        });
    }).fail(function (xhr, status, error) {
        console.error('AJAX request failed:', status, error);
    });
}