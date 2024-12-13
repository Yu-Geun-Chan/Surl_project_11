<script lang="ts">
	import createClient from 'openapi-fetch';

	import type { paths, components } from '$lib/backend/apiV1/schema';

	type Client = ReturnType<typeof createClient<paths>>;

	const client: Client = createClient<paths>({
		baseUrl: import.meta.env.VITE_CORE_API_BASE_URL,
		credentials: 'include'
	});

	let member: components['schemas']['MemberDto'] | null = $state(null);
	let errorMessage: string | null = $state(null);

	async function getMe() {
		const { data, error } = await client.GET('/api/v1/members/me');

		if (data) {
			member = data.data.item;
		} else if (error) {
			errorMessage = error.msg;
		}
	}

	$effect(() => {
		getMe();
	});
</script>

{#if member}
	<div>
		<div>
			<span>아이디</span>
			<span>{member.username}</span>
		</div>
		<div>
			<span>닉네임</span>
			<span>{member.nickname}</span>
		</div>
		<div>
			<span>가입일</span>
			<span>{member.createDate}</span>
		</div>
		<div>
			<span>수정일</span>
			<span>{member.modifyDate}</span>
		</div>
	</div>
{:else if errorMessage !== null}
	<div>에러 발생: {errorMessage}</div>
{/if}
