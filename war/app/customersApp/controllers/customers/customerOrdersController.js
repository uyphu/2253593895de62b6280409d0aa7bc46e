(function () {

    var injectParams = ['$scope','$routeParams', '$window', 'dataService', '$location', 'orderService', 
                        'modalService', '$timeout'];

    var CustomerOrdersController = function ($scope, $routeParams, $window, dataService, $location, 
    					orderService, modalService, $timeout) {
        var vm = this,
            customerId = ($routeParams.customerId) ? parseInt($routeParams.customerId) : 0;

        vm.customer = {};
        vm.ordersTotal = 0.00;

        init();

        function init() {
            //Load order endpoint
            if (!AppConstant.ORDER_ENDPOINT_LOADED) {
				orderService.init().then(function(){
				},
				function(){
					console.log(ErrorCode.ERROR_INIT_ENDPOINT_SERVICE);
				});
			} 
            
            loadData();
        }
        
        var loadData = function() {
        	if (customerId > 0) {
                dataService.getCustomer(customerId)
                .then(function (customer) {
                    vm.customer = customer;
                    $scope.$broadcast('customer', customer);
                }, function (error) {
                    $window.alert("Sorry, an error occurred: " + error.message);
                });
            }
        }
        
        vm.navigate = function (url) {
        	url = url + '/' + vm.customer.id;
            $location.path(url);
        };
        
        vm.deleteOrder = function(id, product) {
        	alert(product);
//            var modalOptions = {
//                closeButtonText: 'Cancel',
//                actionButtonText: 'Delete Order',
//                headerText: 'Delete ' + product + '?',
//                bodyText: 'Are you sure you want to delete this Order?'
//            };
//
//            modalService.showModal({}, modalOptions).then(function (result) {
//                if (result === 'ok') {
//                	orderService.deleteOrder(id).then(function () {
//                        //onRouteChangeOff(); //Stop listening for location changes
//                        //$location.path('/customers');
//                		loadData();
//                    }, processError);
//                }
//            });
        }
        
        function processSuccess() {
            $scope.editForm.$dirty = false;
            vm.updateStatus = true;
            vm.title = 'Edit';
            vm.buttonText = 'Update';
            startTimer();
        }

        function processError(error) {
            vm.errorMessage = error.message;
            startTimer();
        }

        function startTimer() {
            timer = $timeout(function () {
                $timeout.cancel(timer);
                vm.errorMessage = '';
                vm.updateStatus = false;
            }, 3000);
        }
        
        
    };

    CustomerOrdersController.$inject = injectParams;

    angular.module('customersApp').controller('CustomerOrdersController', CustomerOrdersController);

}());