console.log("board register in");

//버튼을 클릭했을 때 input 파일을 클릭한 것과 같아짐
document.getElementById('trigger').addEventListener('click',()=>{
    document.getElementById('file').click();
});

//정규표현식을 통해 파일의 형식을 제한
//실행파일 막기(exe,bat,sh,mis,dll,jar)
//파일 사이즈 체크 (20M 사이즈보다 크면 막기)(내가 정한 max가 20M)
//이미지파일만 허용(jpg,png,jpeg,gif,bmp)

const regExp = new RegExp("\.(exe|sh|bat|mis|dll|jar)$");
const regExpImg = new RegExp("\.(jpg|jpeg|png|gif|bmp)$");
const maxSize = 1024*1024*20;

//Validation 규칙설정
//return 0/1로 리턴
function fileValidation(name, fileSize){
    let fileName =name.toLowerCase(); //들어오는 파일이름을 전부 소문자로 변경(확장자도)
    if(regExp.test(fileName)) { //파일 확장자에 실행파일 확장자가 있다면 0(false)
        return 0;
    } else if(fileSize>maxSize) {
        return 0;
    } else if(!regExpImg.test(fileName)) { //이미지 파일이 아니면
        return 0;
    } else {
        return 1;
    }
}

//첨부파일에 따라 등록이 가능한지 체크 함수
document.addEventListener('change',(e)=>{
    console.log(e.target);
    if(e.target.id==='file'){
        //file이 multiple이기 때문에 여러개의 파일이 배열로 들어옴
        const fileObj = document.getElementById('file').files;
        console.log(fileObj);

        //한번 true가 된 disabled는 다시 false로 돌아올 수 없음
        //새로들어오면 버튼 활성화
        document.getElementById('regBtn').disabled=false;

        let div=document.getElementById('fileZone');
        div.innerHTML=''; //기존에 등록한 파일이 있다면 지우기

        let ul=`<ul class="list-group">`;
        //각각의 파일은 fileValidator에 의해 리턴여부를 체크
        //모든 파일에 return이 1이어야 가능
        //모든 파일의 return값을 곱해서 isOk에 삽입(하나라도 0이 뜨면 0->업로드 불가능)
        let isOk = 1; 
        for(let file of fileObj) {
            let validResult = fileValidation(file.name, file.size); //하나의 파일에 대한 결과
            isOk *= validResult;
            ul+=`<li class="list-group-item">`;
            ul+=`<div>${validResult?"업로드가능":"업로드불가능"}</div>${file.name} `;
            ul+=`<span class="badge text-bg-${validResult?'success':'danger'}">${file.size}</span>`;
            ul+=`</li>`;
        }
        ul+=`</ul>`;
        div.innerHTML=ul;

        //업로드가 불가능한 파일이 하나라도 있다면 등록버튼 비활성화
        if(isOk==0) {
            document.getElementById('regBtn').disabled=true; //비활성화
        }
    }
})