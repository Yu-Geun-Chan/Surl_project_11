<script lang="ts">
	import { goto } from '$app/navigation';
	import createClient from 'openapi-fetch';
	import type { paths } from '$lib/backend/apiV1/schema';
	type Client = ReturnType<typeof createClient<paths>>;
	const client: Client = createClient<paths>({
		baseUrl: import.meta.env.VITE_CORE_API_BASE_URL,
		credentials: 'include'
	});

	async function logout() {
		await client.DELETE('/api/v1/members/logout');
		goto('/');
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
