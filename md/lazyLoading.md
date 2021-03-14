이미지 LazyLoading 기법
-
이미지는 모든 웹 사이트와 어플리케이션에서 매우 중요한 요소이다.  
그런데 이미지는 웹 사이트 성능에 가장 많은 영향을 주는 요소이다.  
[HTTP Archive data](https://httparchive.org/reports/state-of-images?start=2016_12_01&end=latest&view=list "") 에 들어가보면 알겠지만,
전체 트래픽 중, 이미지가 차지하는 트래픽이 얼마나 큰 지 알 수 있다.
따라서 우리는 이미지를 그대로 쓰면서 웹 페이지를 빠르게 로딩할 수 있는 방법을 찾아야 함

* Lazy Loading 은 페이지 내에서 바로 필요하지 않은 이미지들의 로딩 속도를 뒤로 늦추는 것
   1. 성능 향상 -> 페이지 초기 로딩시 필요로 하는 이미지 수를 줄일 수 있음
   2. 비용 감소 -> 이미지를 로딩하기 전 페이지를 이탈하는 유저들이 많을 수록 LazyLoading 에 의해 아낄 수 있는 트래픽이 많으므로 비용이 감소합니다.


방법
-
대표적인 방법
```html
<img src="https://ik.imagekit.io/demo/default-image.jpg" />

<img data-src="https://ik.imagekit.io/demo/default-image.jpg" />
```
src 속성에 넣으면 무조건 이미지를 로딩함
그래서 일단 data-src 속성에 값을 넣고 이미지 로딩하는 것을 막아야 함

이후 
1. 자바스크립트 이벤트를 통한 로딩
2. Intersection Observer API 를 이용한 이미지 로딩

을 통해서 Lazy Loading을 할 수 있다.

자바스크립트 이벤트를 통한 로딩
-
scroll, resize, orientationChange 이벤트 리스너를 이용해서 로딩함
```js
document.addEventListener("DOMContentLoaded", function() {
  var lazyloadImages = document.querySelectorAll("img.lazy");    
  var lazyloadThrottleTimeout;
  
  function lazyload () {
    if(lazyloadThrottleTimeout) {
      clearTimeout(lazyloadThrottleTimeout);
    }    
    
    lazyloadThrottleTimeout = setTimeout(function() {
        var scrollTop = window.pageYOffset;
        lazyloadImages.forEach(function(img) {
            if(img.offsetTop < (window.innerHeight + scrollTop)) {
              img.src = img.dataset.src;
              img.classList.remove('lazy');
            }
        });
        if(lazyloadImages.length == 0) { 
          document.removeEventListener("scroll", lazyload);
          window.removeEventListener("resize", lazyload);
          window.removeEventListener("orientationChange", lazyload);
        }
    }, 20);
  }
  
  document.addEventListener("scroll", lazyload);
  window.addEventListener("resize", lazyload);
  window.addEventListener("orientationChange", lazyload);
});
```
img.src = img.dataset.src; 이 줄로 data-src 속성을 src 속성에 넣어주는 것



Intersection Observer API
-
```js
document.addEventListener("DOMContentLoaded", function() {
  var lazyloadImages;    

  if ("IntersectionObserver" in window) {
    lazyloadImages = document.querySelectorAll(".lazy");
    var imageObserver = new IntersectionObserver(function(entries, observer) {
      entries.forEach(function(entry) {
        if (entry.isIntersecting) {
          var image = entry.target;
          image.src = image.dataset.src;
          image.classList.remove("lazy");
          imageObserver.unobserve(image);
        }
      });
    });

    lazyloadImages.forEach(function(image) {
      imageObserver.observe(image);
    });
  } else {  
    var lazyloadThrottleTimeout;
    lazyloadImages = document.querySelectorAll(".lazy");
    
    function lazyload () {
      if(lazyloadThrottleTimeout) {
        clearTimeout(lazyloadThrottleTimeout);
      }    

      lazyloadThrottleTimeout = setTimeout(function() {
        var scrollTop = window.pageYOffset;
        lazyloadImages.forEach(function(img) {
            if(img.offsetTop < (window.innerHeight + scrollTop)) {
              img.src = img.dataset.src;
              img.classList.remove('lazy');
            }
        });
        if(lazyloadImages.length == 0) { 
          document.removeEventListener("scroll", lazyload);
          window.removeEventListener("resize", lazyload);
          window.removeEventListener("orientationChange", lazyload);
        }
      }, 20);
    }

    document.addEventListener("scroll", lazyload);
    window.addEventListener("resize", lazyload);
    window.addEventListener("orientationChange", lazyload);
  }
})

```

"IntersectionObserver" in window 이면
IntersectionObserver 객체를 생성해서 data-src를 src로 변경하는 함수를 넘겨주고
아니면 스크롤 될 때 할 수 있도록



또 다른 LazyLoading 방법 (크롬에서 지원)
-
```html
<img src="example.jpg" loading="lazy" alt="..." />
<iframe src="example.html" loading="lazy"></iframe>
```
이런 식으로 속성을 줘서 해결할 수 있음 





