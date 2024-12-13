<script lang="ts">
	async function logout() {
		const rs = await fetch(`${import.meta.env.VITE_CORE_API_BASE_URL}/api/v1/members/logout`, {
			method: 'DELETE',
			credentials: 'include'
		}).then((res) => res.json());
		console.log(rs);
	}
</script>

<header>
	<nav>
		<a href="/">메인</a>
		<a href="/member/login">로그인</a>
		<button type="button" on:click|preventDefault={logout}>로그아웃</button>
		<a href="/member/me">내 정보</a>
	</nav>
</header>

<slot></slot>
