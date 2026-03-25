<!DOCTYPE html>
<head>
</head>

<body>

<#list persons as p>
    <oi>${p.name} - ${p.phone.number}</oi>
    <br>
</#list>

<a href="/persons/create">создать еще одного пользователя</a>

</body>