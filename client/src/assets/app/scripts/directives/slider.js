assetsApp.directive("mySwipe", function(){
	return{
		link : function(scope, element){

			var active = 0;

			var tabsa = document.querySelectorAll('.tab-fixed li a');
			var tabs = document.querySelectorAll('.tab-fixed li');

			function swapActive(newactive){
				tabs[active].classList.remove('active');
				tabs[newactive].classList.add('active');
				active = newactive;
			};

			var mySwipe = Swipe(document.getElementById("slider"), 
				{continuous : false,
				callback : function(index, element){
						swapActive(index);
					}
				}
			);

			active = mySwipe.getPos();
			swapActive(active);



			var getTarget = function (target) {
		    var i, tabs = document.querySelectorAll('.tab-fixed li a');
		    for (; target && target !== document; target = target.parentNode) {
		      for (i = tabs.length; i--;) { if (tabs[i] === target) return {index : i, target : target}; }
			   }
		  };

		  document.getElementById('tabs').addEventListener('touchend', function (e) {

		    var activeTab;
		    var activeBody;
		    var targetBody;
		    var targetTab;
		    var className     = 'active';
		    var classSelector = '.' + className;
		    var targetObj  = getTarget(e.target);
		    var targetAnchor  = targetObj.target;
		    var targetIndex  = targetObj.index;

		    mySwipe.slide(targetIndex, 300);

		    if (!targetAnchor) return;

		    e.preventDefault();

		    e.preventDefault();

		    targetTab = targetAnchor.parentNode;
		    activeTab = targetTab.parentNode.querySelector(classSelector);

		    // Highlight the target tab
		    if (activeTab) activeTab.classList.remove(className);
		    targetTab.classList.add(className);

		    // If the target body doesn't exist, don't do anything
		    targetBody = document.querySelector(targetAnchor.hash);
		    if (!targetBody) return;

		    // Set the target body as active
		    activeBody = targetBody.parentNode.querySelector(classSelector);
		    if (activeBody) activeBody.classList.remove(className);
		    targetBody.classList.add(className);
		  });
		}
	}
});
