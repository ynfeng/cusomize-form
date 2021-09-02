# 自定义表单

## 演示地址

> 注: 国内无法访问

```
https://customizeform.herokuapp.com/
```

创建表单示例

```
curl --location --request POST 'https://customizeform.herokuapp.com/v1/form-definitions' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "出差申请单",
    "items": [
        {
            "name": "destAddress",
            "screenName": "目的地",
            "type": "AddressSelect",
            "params": {
                "provinceName": "province",
                "provinceScreenName": "省",
                "cityName": "city",
                "cityScreenName": "市",
                "areaName": "area",
                "areaScreenName": "区"
            }
        },
        {
            "name": "dept",
            "screenName": "部门",
            "type": "DepartmentSelect"
        },
        {
            "name": "reason",
            "screenName": "出差原因",
            "type": "SingleLineText"
        }
    ]
}'
```

