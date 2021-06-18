function Paging(pagingid, displaycntid, currentpageid, searchfn, totalcnt){
	this.pagesize = 10;
	this.pagingElement = document.getElementById(pagingid); // 페이징 오브젝트를 넣을 객체
	this.displayObj = document.getElementById(displaycntid); // 한번에 보여지는 데이터수
	this.currentPageObj = document.getElementById(currentpageid); // 현재 페이지 번호가 기입된 아이디
	this.totalcnt = Number(totalcnt);
	this.callback = searchfn;
}

Paging.prototype.move_page_function = function(pagingNum, currentPageObj, callback) {
	return function move_page(e) {
		if ( typeof(pagingNum) === 'undefined') {
			return false;
		}
		currentPageObj.value = pagingNum;
		return callback();
	}
}

Paging.prototype.init = function() {
	this.generatePaging();
};

Paging.prototype.regenerator = function(totalCnt) {
	this.totalcnt = Number(totalCnt);
	if (this.totalcnt < 1) {
		this.remove();
	} else {
		this.generatePaging();
	}
}
Paging.prototype.remove = function() {
	this.pagingElement.innerHTML = '';
}

Paging.prototype.generatePaging = function() {
	this.pagingElement.innerHTML = '';
	let displayCnt = Number(this.displayObj.value);
	let currentPage = Number(this.currentPageObj.value);
	let totalPage = Math.ceil(this.totalcnt / displayCnt); // 전체 페이지

	if(currentPage < 1) {
		currentPage = 1;
	} else if ( currentPage > totalPage ){
		currentPage = totalPage;
	}

	let minPage = 1;
	let maxPage = 1;

	if(totalPage <= this.pagesize) {
		maxPage = totalPage;
	} else {

		let possibleMin;
		let possibleMax;

		if (currentPage % this.pagesize === 0) {
			possibleMin = (Math.floor(currentPage/this.pagesize) - 1) * this.pagesize + 1;
			possibleMax = Math.floor(currentPage/this.pagesize) * this.pagesize;
		} else {
			possibleMin = Math.floor(currentPage/this.pagesize) * this.pagesize + 1;
			possibleMax = (Math.floor(currentPage/this.pagesize) + 1) * this.pagesize;
		}

		if(possibleMax < this.pagesize) {
			minPage = 1;
			maxPage = this.pagesize;
		} else if (possibleMax > totalPage){
			minPage = possibleMin;
			maxPage = totalPage;
		} else {
			minPage = possibleMin;
			maxPage = possibleMax;
		}
	}

	let beforeCurrentPage = (minPage - this.pagesize < 1) ? 1 : minPage - this.pagesize;
	let afterCurrentPage = (maxPage + 1 >= totalPage) ? totalPage : maxPage + 1;

	let firstElement = document.createElement('a');
	firstElement.classList.add('pageFirst');
	firstElement.addEventListener('click', this.move_page_function(1, this.currentPageObj, this.callback), false);
	firstElement.innerText = '처음';
	this.pagingElement.appendChild(firstElement);

	let prevElement = document.createElement('a');
	prevElement.classList.add('pageBefore');
	prevElement.addEventListener('click', this.move_page_function(beforeCurrentPage, this.currentPageObj, this.callback), false);
	prevElement.innerText = '이전';
	this.pagingElement.appendChild(prevElement);

	for(let i = minPage; i <= maxPage; i++){
		let pageElement = document.createElement('a');
		pageElement.addEventListener('click', this.move_page_function(i, this.currentPageObj, this.callback), false);
		if ( i === currentPage) {
			pageElement = document.createElement('strong');
		}
		pageElement.innerText = i;
		this.pagingElement.appendChild(pageElement);
	}
	let nextElement = document.createElement('a');
	nextElement.classList.add('pageNext');
	nextElement.addEventListener('click', this.move_page_function(afterCurrentPage, this.currentPageObj, this.callback), false);
	nextElement.innerText = '다음';
	this.pagingElement.appendChild(nextElement);

	let lastElement = document.createElement('a');
	lastElement.classList.add('pageLast');
	lastElement.addEventListener('click', this.move_page_function(totalPage, this.currentPageObj, this.callback), false);
	lastElement.innerText = '마지막';
	this.pagingElement.appendChild(lastElement);
}