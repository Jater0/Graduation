export const showLoginModel = () => {
	uni.showModal({
		title: '登录提示',
		content: '您现在还没登录, 请先登录再进行操作',
		success: (res) => {
			if(res.confirm) {
				uni.switchTab({
					url: '/pages/tabbar/my/my'
				})
			} else if(res.cancel) {
			}
		}
	})
}

export const convertDate = (date) => {
	var dateMiddle = new Date(date).toJSON()
	return new Date(+ new Date(dateMiddle) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
}

export const getID = (length) => {
	return Number(Math.random().toString().substring(3, length) + Date.now()).toString(36)
}