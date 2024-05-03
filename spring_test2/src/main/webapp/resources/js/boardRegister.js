console.log("register js in");

//trigger 버튼 처리
document.getElementById('trigger').addEventListener('click',()=>{
    document.getElementById('file').click();
})

//정규표현식(실행파일만 막도록) / 파일 최대 사이즈
const regExp = new RegExp("\.(exe|sh|bat|mis|dll|jar)$");
const maxSize = 1024*1024*20;

function fileValidation(fileName, fileSize) {
    if(regExp.test(fileName)){
        return 0;
    } else if(fileSize>maxSize){
        return 0;
    } else {
        return 1;
    }
}

document.addEventListener('change',(e)=>{
    if(e.target.id=='file'){ //파일에 변화가 생겼다면
        //input type="file" element에 저장된 file의 정보를 가져오는 property
        const fileObj = document.getElementById('file').files;
        console.log(fileObj);
        //한번 disabled 되면 다시 풀어질 수 없음. 버튼을 원래 상태(able)로 복구
        document.getElementById('regBtn').disabled = false;

        //등록된 file의 정보를 fileZone에 기록
        let div = document.getElementById('fileZone');
        div.innerHTML='';
        //ul > li로 파일 목록 추가
        //<ul class="list-group list-group-flush">
        //<li class="list-group-item">An item</li>
        
        //여러개의 등록파일이 모두 검증을 통과해야하기 때문에
        //isOk * 로 각각 파일이 통과할 때마다 연산을 실행=> 통과 여부 확인
        let isOk = 1;
        let ul = `<ul class="list-group list-group-flush">`;
        for(let file of fileObj) {
            //개별파일 검증 통과 결과
            let validResult = fileValidation(file.name, file.size);
            isOk *= validResult;
            ul +=`<li class="list-group-item">`;
            ul+=`<div class="mb-3">`;
            ul+=`${validResult ? '<div class="fw-bold">업로드가능</div>' : '<div class="fw-bold text-danger">업로드불가능</div>'}`;
            ul+=`${file.name}</div>`;
            ul+=`<span class="badge text-bg-${validResult ? 'success':'danger'}">${file.size}Bytes</span>`;
            ul+=`</li>`;
        }
        ul+=`</ul>`;
        div.innerHTML=ul;

        if(isOk==0) {
            //하나라도 파일이 검증을 통과하지 못하면
            document.getElementById('regBtn').disabled = true;
        }
    }
})