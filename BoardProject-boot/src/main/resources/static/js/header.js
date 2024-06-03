/* 날씨 open api */

// async&await : async pattern

// current date YYYYMMDD
function getCurrentDate() {

    const today = new Date();
    const year = today.getFullYear();
    const month = ('0' + (today.getMonth() + 1)).slice(-2);
    const day = ('0' + (today.getDate())).slice(-2);
    
    // console.log(`${year}${month}${day}`);
    return `${year}${month}${day}`;
}

// service key from config.properties
async function getServiceKey() {

    try {

        const response = await fetch("/getServiceKey");

        return response.text();

    } catch(err) {
        console.log("getServiceKey error : " + err);
    }

}

async function fetchData() {

    const currentDate = getCurrentDate();
    const serviceKey = await getServiceKey(); 
    // console.log(serviceKey);

    // console.log(serviceKey);
    const url = 'http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst';
    
    // URLSearchParams : use querystring from URL -> 
    // URLSearchParams encode data -> to use use decode service key
    const queryParams = new URLSearchParams({
        ServiceKey : serviceKey,
        pageNo : 1,
        numOfRows : 10,
        dataType : 'JSON',
        base_date : currentDate,
        base_time : '0500',
        nx : 60,
        ny : 127
    });
    console.log(url+'?'+ queryParams);
    //await fetch(url + '?' + queryParams)
    try {

        const response = await fetch(`${url}?${queryParams}`);
        const result = await response.json();

        // console.log(result);

        const obj = result.response.body.items.item.reduce((acc, data) => {
            acc[data.category] = data.fcstValue;
            return acc;
        }, {});
        
        // console.log(obj);
        const sky = {
            "1" : "맑음",
            "3" : "구름많음",
            "4" : "흐림"
        }
        // 화면에 뿌리기..
        const weatherInfo = document.querySelector(".weather-info");
        
        // i 요소 생성 (아이콘)
        const icon = document.createElement('i');
        
        // span 요소 생성 (날씨 정보)
        const span = document.createElement('span');
        
        // 첫 번째 p 요소 생성 (기온 정보)
        const p1 = document.createElement('p');
        // 두 번째 p 요소 생성 (비 올 확률 정보)
        const p2 = document.createElement('p');
        // 강수형태(PTY) 코드 : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
        // 강수 형태에 따라 icon 지정
        if( obj.PTY == 0 ) { // 강수 없음
            switch(obj.SKY) { // SKY 상태에 따라 아이콘 지정
                case "1" : 
                    icon.className = 'fa-solid fa-sun'; break;
        
                case "3" :
                    icon.className = 'fa-solid fa-cloud'; break;
               
                case "4" :
                    icon.className = 'fa-solid fa-cloud-sun'; break;
        
            }
        } else if(obj.PTY == 3) { // 눈 올 때
            icon.className = 'fa-solid fa-snowflake';
        } else { // 그외 비올때
            icon.className = 'fa-solid fa-cloud-rain';
        }
        // 하늘 정보
        span.innerText = sky[obj.SKY];
        // 기온
        p1.innerText = `기온 : ${obj.TMP}℃`;
        
        // 강수확률
        p2.innerText = `강수 확률 : ${obj.POP}%`;
        // div에 자식 요소들 추가
        weatherInfo.appendChild(icon);
        weatherInfo.appendChild(span);
        weatherInfo.appendChild(p1);
        weatherInfo.appendChild(p2);
        
    } catch (err) {
        console.log(err);
    }

}

fetchData();
