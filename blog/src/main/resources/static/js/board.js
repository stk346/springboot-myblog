// btb-save가 클릭이 되 save 함수의 #title, #content를 가져와서 /api/board에 $.ajax()의 data: JSON.stringify(data)의 형식으로 날린다.
// 이후 Board, BoardController, BoardApiController, BoardService 등을 순차적으로 작동시키고 정상적으로 완료되면
// alert("글쓰기가 완료돠었습니다"); 수행 후 location.href = "/"; 페이지로 이동

let index = {
    init: function() {
        $("#btn-save").on("click", ()=> {
            this.save();
        });
        $("#btn-delete").on("click", ()=> {
            this.deleteById();
        });
    },

    deleteById: function() {
          var id = $("#id").text();

          $.ajax({
            type: "DELETE",
            url: "/api/board/"+id,
            dataType: "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열. / 생긴게 json이라면 -> javascript 오브젝트로 변경
          }).done(function(resp){ // 성공하면
            alert("삭제가 완료돠었습니다");
            location.href = "/";
          }).fail(function(error){ // 실패하면
            alert(JSON.stringify(error));
          });
    },

    save: function() {
            let data = {
                title: $("#title").val(),
                content: $("#content").val()
            }

            console.log(data);

              $.ajax({
                type: "POST",
                url: "/api/board",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열. / 생긴게 json이라면 -> javascript 오브젝트로 변경
              }).done(function(resp){ // 성공하면
                alert("글쓰기가 완료돠었습니다");
                location.href = "/";
              }).fail(function(error){ // 실패하면
                alert(JSON.stringify(error));
              });
        },
}

index.init();