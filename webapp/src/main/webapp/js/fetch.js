function send( url, method, data){
    fetch(url, {
        method: method,
        body: data
    })
        .then(response => {
            if (response.ok)
                return 0;
        })
        .catch(error => {
            console.error('Error de red:', error);
            return 1;
        });
    return 0;
}
