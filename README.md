# Pharmacy-Find-Directions

## :computer: 프로젝트 설명

사용자가 입력한 약국 주소에 대하여 반경 10KM 내에 가까운 약국 3곳을 거리 순으로 알려주는 기능을 제공합니다

## :mag: 개발환경

- `Java` : Jdk 21
- `Gradle` : gradle(8.8)
- `IDE` : Intellij
- `Framework` : Springboot(3.3)
- `Database` : Maria DB(11), Redis(7)
- `ORM` : Spring Data Jpa

## :star: 프로젝트 주요 기능

#### :telescope: 약국주소검색 외부 API 연동
- 주소를 입력받아 해당 주소의 위도, 경도를 반환하는 서비스
  <details><summary>KakaoAddressSearchService</summary> 
</details>

- 카테고리 코드, 위도, 경도, 반경 거리를 입력받아 해당 주소 반경 내 주소 정보들을 반환하는 서비스
  <details><summary>KakaoCategorySearchAddressService </summary> 
</details>


### :pencil2: 가까운 약국 주소 조회에 적용된 방법들

#### `첫번째`

1. 약국주소에 대한 공공 데이터를 수집 후 데이터베이스 저장
2. 사용자 입력 주소에대한 기본 주소정보를 반환하는 외부 API 사용 (거리 계산 알고리즘, 정렬 필요 O)
3. 반환된 API 데이터와 데이터베이스에 저장된 주소를 기반하여 반경 10KM 약국 3곳을 거리 순 추천


#### `두번째`

1. 사용자가 입력한 주소에 대해 반경 거리이내의 장소 및 거리순을 반환하는 외부API 사용 (거리 계산 알고리즘, 정렬 필요 X)
2. 반환된 API 데이터를 기반으로 반경 10KM 약국 3곳을 거리 순 추천

#### 

## :star: 외부 API 사용 참조 
1. 주소검색하기 : <https://developers.kakao.com/docs/latest/ko/local/dev-guide>
2. 우편번호 검색하기 : <https://postcode.map.daum.net/guide>
3. 지도 바로가기, 로드뷰 바로가기 : <https://apis.map.kakao.com/web/guide/#bigmapurl>
