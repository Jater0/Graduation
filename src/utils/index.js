export const getID = (length) => {
    return Number(Math.random().toString().substring(3, length) + Date.now()).toString(36)
}

export const convertDate = (date) => {
	var dateMiddle = new Date(date).toJSON()
	return new Date(+ new Date(dateMiddle) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
}

export const convertVueHtml = (type, content, covers) => {
	var coverHtml = '<div class="row" style="margin-bottom: 20px">'
	if (covers.length === 0) {
		return `<div>
      <label for="type"><strong>类型: ${type}</strong></label>
      <br />
      <label for="content" style="white-space:normal;"><strong>内容: </strong></label>
	  <br />
      ${content}
	  <br />
	  <label for="covers"><strong>图片:</strong></label>
	  <br />
	  </div`
	}
	for (let i = 0; i < covers.length; i++) {
		coverHtml += `
        <div class="col-4">
          <div class="card-group">
            <div class="card">
              <img
                  src="${covers[i]}"
                  style="width: 100%; height: 100%; overflow: hidden"
                />
            </div>
          </div>
        </div>`
	}
	return `<div>
      <label for="type"><strong>类型: ${type}</strong></label>
      <br />
      <label for="content"><strong>内容: </strong></label>
	  <br />
      ${content}
	  <br />
	  <label for="covers"><strong>图片:</strong></label>
	  <br />
	  ${coverHtml}</div>
	  </div>`
}