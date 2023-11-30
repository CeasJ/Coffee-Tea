const app = angular.module("size-app", []);
app.controller("size-ctrl", function ($scope, $http, $location) {
  $scope.sizes = [];
  $scope.products = [];
  $scope.productSize = [];

  $scope.intialize = function () {
    $http.get(`/rest/size`).then((resp) => {
      $scope.sizes = resp.data;
    });

    $http.get(`/rest/products`).then((resp) => {
      $scope.products = resp.data;
    });

    $http
      .get(`/rest/productsizes`)
      .then((resp) => {
        $scope.productSize = resp.data;
      })
      .catch((error) => {
        $location.path(`/security/login/unauthorized`);
      });
  };
  $scope.intialize();

  $scope.size_of = function (id_product, id_size) {
    if ($scope.productSize) {
      return $scope.productSize.find(
        (ur) => ur.id_product === id_product && ur.id_size === id_size
      );
    }
  };

  $scope.size_changed = function (id_product, id_size) {
    let productSize = $scope.size_of(id_product, id_size);
    if (productSize) {
      $scope.revoke_size(productSize);
    } else {
      let price = $scope.updateProductSizePrice();
      productSize = { id_product: id_product, id_size: id_size, price: price };
      $scope.grant_size(productSize);
    }
  };

  $scope.grant_size = function (productSize) {
    $scope.id_product = productSize.id_product;
    console.log($scope.id_product);

    $scope.id_size = productSize.id_size;
    console.log($scope.id_size);

    $("#priceModal").modal("show");

    $scope.selectRadioBasedOnIdSize(productSize.id_size);
  };

  $scope.add = function () {
    let id_product = $scope.id_product;
    let id_size = $scope.id_size;
    let price = $scope.updateProductSizePrice();
    let productSize = {
      id_product: id_product,
      id_size: id_size,
      price: price,
    };

   
    console.log(productSize);
    $http
      .post(`/rest/productsizes`, productSize)
      .then((resp) => {
        $scope.productSize.push(resp.data);
        $("#priceModal").modal("hide");
        toastr.success("Add size success");
        setTimeout(() => {
          location.reload();
        }, 2000);
      })
      .catch((error) => {});
  };

  $scope.revoke_size = function (productSize) {
    $http
      .delete(`/rest/productsizes/${productSize.id}`, productSize)
      .then((resp) => {
        let index = $scope.productSize.findIndex(
          (product) => product.id == productSize.id_product
        );
        $scope.productSize.splice(index, 1);
        toastr.success("Delete success");
        setTimeout(() => {
          location.reload();
        }, 2000);
      })
      .catch((error) => {});
  };

  $scope.updateProductSizePrice = function () {
    return $scope.price;
  };

  $scope.selectRadioBasedOnIdSize = function (id_size) {
    switch (id_size) {
      case 1:
        $scope.selectedSize = "S";
        $scope.radioBlocked = {
          S: false,
          M: true,
          L: true,
        };
        break;
      case 2:
        $scope.selectedSize = "M";
        $scope.radioBlocked = {
          S: true,
          M: false,
          L: true,
        };
        break;
      case 3:
        $scope.selectedSize = "L";
        $scope.radioBlocked = {
          S: true,
          M: true,
          L: false,
        };
        break;
      default:
        break;
    }
  };
});
