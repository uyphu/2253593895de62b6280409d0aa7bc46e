/**
 * CustomersService.js
 * Author: Phu
 * Date: Apr 10, 2015
 */

'use strict';

/**
 * Initialize.
 */
(function (){
	
	var injectParams = ['$q'];
	
	var customersFactory = function($q) {
		var factory = {}; 
		factory.init = function() {
			//return CloudUtils.prototype.loadClouds($q);
			//return CloudUtils.prototype.loadCustomerClouds($q);
			if (!AppConstant.ALL_ENDPOINT_LOADED) {
				var hwdefer=$q.defer();
//			    var oauthloaddefer=$q.defer();
//			    var oauthdefer=$q.defer();
			    
			    if (!AppConstant.CUSTOMER_ENDPOINT_LOADED) {
			    	gapi.client.load('customerendpoint', AppConstant.ENDPOINT_VERSION, function() {
						AppConstant.CUSTOMER_ENDPOINT_LOADED = true;
						hwdefer.resolve(gapi);
						console.log('customerendpoint loaded: ' + AppConstant.CUSTOMER_ENDPOINT_LOADED);
						if (--AppConstant.ENDPOINT_LOADED_NUM == 0) {
							console.log('Loaded all clouds.');
							AppConstant.ALL_ENDPOINT_LOADED = true;
						}
						
					}, AppConstant.ROOT_API);
			    	
			    	if (!AppConstant.STATE_ENDPOINT_LOADED) {
			    		gapi.client.load('stateendpoint', AppConstant.ENDPOINT_VERSION, function() {
							AppConstant.STATE_ENDPOINT_LOADED = true;
							hwdefer.resolve(gapi);
							console.log('stateendpoint loaded: ' + AppConstant.CUSTOMER_ENDPOINT_LOADED);
							if (--AppConstant.ENDPOINT_LOADED_NUM == 0) {
								console.log('Loaded all clouds.');
								AppConstant.ALL_ENDPOINT_LOADED = true;
							}
							
						}, AppConstant.ROOT_API);
			    	}
			    	
			    	var chain = hwdefer.promise;
					
					return chain;
			    } else {
			    	return hwdefer.promise;
			    }
			   
				//var chain=$q.all([hwdefer.promise,oauthloaddefer.promise]);
			    
			}
		}
		
		factory.doCall = function(callback) {
			var p=$q.defer();
			
			gapi.auth.authorize({client_id: AppConstant.CLIENT_ID, scope: AppConstant.SCOPE,
				immediate: true}, function(){
		       var request = gapi.client.oauth2.userinfo.get().execute(function(resp) {
			       if (!resp.code) {
			           p.resolve(gapi);
			       } else {
			           p.reject(gapi);
			       }
		       });
		   });
		   return p.promise;
		}
		
		factory.sigin = function(callback) {
			 gapi.auth.authorize({client_id: AppConstant.CLIENT_ID, scope: AppConstant.SCOPE,
	                immediate: false}, callback);
		}
		
		factory.getCustomersSummary = function (pageIndex, pageSize) {
            return getPagedResource('customersSummary', pageIndex, pageSize);
        };
		
		function getPagedResource(baseResource, pageIndex, pageSize) {
            var data = {};
			var p=$q.defer();
			var requestData = {};
			requestData.cursor = null;
			requestData.count = pageSize;
			gapi.client.customerendpoint.getCustomersSumary(requestData).execute(function(resp) {
				var custs = resp.items;
				extendCustomers(custs);
				data.results = resp.items;
				data.totalRecords = custs.length;
				p.resolve(data);
			});
			return p.promise;
        }
		
		function extendCustomers(customers) {
            var custsLen = customers.length;
            //Iterate through customers
            for (var i = 0; i < custsLen; i++) {
                var cust = customers[i];
                if (!cust.orders) continue;

                var ordersLen = cust.orders.length;
                for (var j = 0; j < ordersLen; j++) {
                    var order = cust.orders[j];
                    order.orderTotal = order.quantity * order.price;
                }
                cust.ordersTotal = ordersTotal(cust);
            }
        }
		
		factory.getCustomers = function (pageIndex, pageSize) {
            return getPagedResource('customers', pageIndex, pageSize);
        }


        factory.getStates = function () {
//            return $http.get(serviceBase + 'states').then(
//                function (results) {
//                    return results.data;
//                });
        	var data = {};
			var p=$q.defer();
			var requestData = {};
			requestData.cursor = null;
			requestData.count = null;
        	gapi.client.stateendpoint.listState(requestData).execute(function(resp) {
        		console.log(resp)
        		p.resolve(resp.items);
        	});
        	return p.promise; 
        }

        factory.checkUniqueValue = function (id, property, value) {
            if (!id) id = 0;
            return $http.get(serviceBase + 'checkUnique/' + id + '?property=' + property + '&value=' + escape(value)).then(
                function (results) {
                    return results.data.status;
                });
        };

        factory.insertCustomer = function (customer) {
            return $http.post(serviceBase + 'postCustomer', customer).then(function (results) {
                customer.id = results.data.id;
                return results.data;
            });
        };

        factory.newCustomer = function () {
            return $q.when({id: 0});
        };

        factory.updateCustomer = function (customer) {
            return $http.put(serviceBase + 'putCustomer/' + customer.id, customer).then(function (status) {
                return status.data;
            });
        };

        factory.deleteCustomer = function (id) {
            return $http.delete(serviceBase + 'deleteCustomer/' + id).then(function (status) {
                return status.data;
            });
        };
		
		return factory;
	}
	
	customersFactory.$inject = injectParams;
	
	angular.module(AppConstant.APP_NAME).factory('customersService', customersFactory);
	
}());



