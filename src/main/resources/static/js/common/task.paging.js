function Paging(pagingid, displaycntid, currentpageid, searchfn, totalcnt){
	this.pagesize = 10;
	this.pagingElement = document.getElementById(pagingid);
	this.displayObj = document.getElementById(displaycntid);
	this.currentPageObj = document.getElementById(currentpageid);
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

	let firstElementLi = document.createElement('li');
	let firstElement = document.createElement('a');
	firstElement.classList.add('pageFirst');
	firstElement.addEventListener('click', this.move_page_function(1, this.currentPageObj, this.callback), false);
	firstElement.innerText = '처음';
	firstElementLi.appendChild(firstElement)
	this.pagingElement.appendChild(firstElementLi);

	let prevElementLi = document.createElement('li');
	let prevElement = document.createElement('a');
	prevElement.classList.add('pageBefore');
	prevElement.addEventListener('click', this.move_page_function(beforeCurrentPage, this.currentPageObj, this.callback), false);
	prevElement.innerText = '이전';
	prevElementLi.appendChild(prevElement)
	this.pagingElement.appendChild(prevElementLi);

	for(let i = minPage; i <= maxPage; i++) {
		let pageElementLi = document.createElement('li');
		let pageElement = document.createElement('a');
		pageElement.addEventListener('click', this.move_page_function(i, this.currentPageObj, this.callback), false);
		if ( i === currentPage) pageElementLi.classList.add("active");
		pageElement.innerText = i;
		pageElementLi.appendChild(pageElement)
		this.pagingElement.appendChild(pageElementLi);
	}

	let nextElementLi = document.createElement('li');
	let nextElement = document.createElement('a');
	nextElement.classList.add('pageNext');
	nextElement.addEventListener('click', this.move_page_function(afterCurrentPage, this.currentPageObj, this.callback), false);
	nextElement.innerText = '다음';
	nextElementLi.appendChild(nextElement);
	this.pagingElement.appendChild(nextElementLi);

	let lastElementLi = document.createElement('li');
	let lastElement = document.createElement('a');
	lastElement.classList.add('pageLast');
	lastElement.addEventListener('click', this.move_page_function(totalPage, this.currentPageObj, this.callback), false);
	lastElement.innerText = '마지막';
	lastElementLi.appendChild(lastElement);
	this.pagingElement.appendChild(lastElementLi);
}
